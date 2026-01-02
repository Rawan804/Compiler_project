package visitor;

import AST.*;
import antlr.python_parser;
import antlr.python_parserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

import symbol_table.SymbolRow;
import symbol_table.SymbolTable;

public class PythonASTVisitor extends python_parserBaseVisitor<Node> {
    private final SymbolTable symbolTable = new SymbolTable();

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public Node visitProg(python_parser.ProgContext ctx) {
        symbolTable.allocate();
        ProgramNode program = new ProgramNode();
        for (var st : ctx.stat()) {
            Node node = visit(st);
            if (node != null) program.addStatement(node);
        }
        symbolTable.free();
        return program;
    }

    @Override
    public Node visitImportStmt(python_parser.ImportStmtContext ctx) {
        int line = ctx.start.getLine();
        SymbolRow row = symbolTable.insert("import");
        row.setType("import");
        row.setLine(line);

        if (ctx.FROM() != null) {
            IdentifierNode module = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());

                for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                    SymbolRow r = symbolTable.insert(ctx.IDENTIFIER(i).getText());
                    r.setType("imported-name");
                    r.setLine(line);
                    r.setAttribute("module", ctx.IDENTIFIER(0).getText());
                }


            List<IdentifierNode> names = new ArrayList<>();
            for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                names.add(new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
            }
            return new ImportNode(line, module, names, null, true);
        }


        if (ctx.IMPORT() != null && ctx.AS() == null) {
            List<IdentifierNode> names = new ArrayList<>();
            for (int i = 0; i < ctx.IDENTIFIER().size(); i++) {
                names.add(new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
            }

            return new ImportNode(line, null, names, null, false);
        }

        if (ctx.AS() != null) {
            IdentifierNode module = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());
            IdentifierNode alias = new IdentifierNode(line, ctx.IDENTIFIER(1).getText());
            return new ImportNode(line, module, new ArrayList<>(), alias, false);
        }

        return null;
    }

    @Override
    public Node visitWithStmt(python_parser.WithStmtContext ctx) {
        int line = ctx.start.getLine();

        Node contextExpr = visit(ctx.expr());

        IdentifierNode optionalVar = null;
        if (ctx.IDENTIFIER() != null) {
            optionalVar = new IdentifierNode(ctx.IDENTIFIER().getSymbol().getLine(), ctx.IDENTIFIER().getText());
            SymbolRow row = symbolTable.insert(optionalVar.getName());
            row.setType("with-var");
            row.setLine(line);
        }

        List<Node> body = new ArrayList<>();
        for (var st : ctx.stat()) {
            Node n = visit(st);
            if (n != null) body.add(n);
        }

        return new WithNode(line, contextExpr, optionalVar, body);
    }


    @Override
    public Node visitTryStmt(python_parser.TryStmtContext ctx) {
        int line = ctx.start.getLine();
        List<python_parser.StatContext> allStats = ctx.stat();
        int tryStart = ctx.TRY().getSymbol().getTokenIndex();
        int exceptStart = ctx.EXCEPT(0).getSymbol().getTokenIndex();
        List<Node> tryBody = collectStatsBetween(allStats, tryStart, exceptStart);
        IdentifierNode exceptType = null;
        if (ctx.IDENTIFIER() != null) {
            exceptType = new IdentifierNode(ctx.IDENTIFIER(0).getSymbol().getLine(), ctx.IDENTIFIER(0).getText());
            SymbolRow exRow = symbolTable.insert(exceptType.getName());
            exRow.setType("exception");
            exRow.setLine(line);
        }

        List<List<Node>> exceptBodies = new ArrayList<>();
        int exceptBodyStart = ctx.COL(1).getSymbol().getTokenIndex();
        List<Node> exceptBody = collectStatsBetween(allStats, exceptBodyStart, Integer.MAX_VALUE);
        exceptBodies.add(exceptBody);

        return new TryNode(line, tryBody, List.of(exceptType), exceptBodies);
    }


    @Override
    public Node visitClassState(python_parser.ClassStateContext ctx) {
        int line = ctx.start.getLine();
        python_parser.ClassStmtContext classStmtCtx = ctx.classStmt();
        IdentifierNode className = new IdentifierNode(line, classStmtCtx.IDENTIFIER().getText());
        SymbolRow row = symbolTable.insert(className.getName());
        row.setType("class");
        row.setLine(line);

        symbolTable.allocate();
        List<Node> body = new ArrayList<>();
        for (var st : classStmtCtx.stat()) {
            body.add(visit(st));
        }

        symbolTable.free();
        return new ClassNode(line, className, body);
    }

    @Override
    public Node visitDefState(python_parser.DefStateContext ctx) {
        int line = ctx.start.getLine();
        python_parser.DefStmtContext defCtx = ctx.defStmt();
        symbolTable.allocate();
        IdentifierNode funcName = new IdentifierNode(line, defCtx.IDENTIFIER(0).getText());

        SymbolRow funcRow = symbolTable.insert(funcName.getName());
        funcRow.setType("function");
        funcRow.setLine(line);

        List<IdentifierNode> parameters = new ArrayList<>();
        for (int i = 1; i < defCtx.IDENTIFIER().size(); i++) {
            IdentifierNode param = new IdentifierNode(line, defCtx.IDENTIFIER(i).getText());
            parameters.add(param);

            SymbolRow pRow = symbolTable.insert(param.getName());
            pRow.setType("parameter");
            pRow.setLine(line);
        }

        List<Node> body = new ArrayList<>();
        for (var st : defCtx.stat()) {
            Node n = visit(st);
            if (n != null) body.add(n);
        }

        symbolTable.free();

        return new FunctionDefNode(line, funcName, parameters, body, new ArrayList<>());
    }


    @Override
    public Node visitDecoratedDefState(python_parser.DecoratedDefStateContext ctx) {
        int line = ctx.start.getLine();

        python_parser.DecoratedDefContext decoratedCtx = ctx.getRuleContext(python_parser.DecoratedDefContext.class, 0);
        python_parser.DefStmtContext defCtx = decoratedCtx.defStmt();
        IdentifierNode funcName = new IdentifierNode(line, defCtx.IDENTIFIER(0).getText());
        SymbolRow funcRow = symbolTable.insert(funcName.getName());
        funcRow.setType("function");
        funcRow.setLine(line);
        symbolTable.allocate();

        List<IdentifierNode> parameters = new ArrayList<>();
        List<org.antlr.v4.runtime.tree.TerminalNode> paramList = defCtx.IDENTIFIER().subList(1, defCtx.IDENTIFIER().size());
        for (var p : paramList) {
            IdentifierNode paramNode = new IdentifierNode(line, p.getText());
            parameters.add(paramNode);
            SymbolRow paramRow = symbolTable.insert(paramNode.getName());
            paramRow.setType("parameter");
            paramRow.setLine(line);
        }
        List<Node> body = new ArrayList<>();
        for (var stmtCtx : defCtx.stat()) {
            Node n = visit(stmtCtx);
            if (n != null) body.add(n);
        }
        List<Node> decorators = new ArrayList<>();
        List<python_parser.DecoratorContext> decoratorList = decoratedCtx.getRuleContexts(python_parser.DecoratorContext.class);
        for (var decCtx : decoratorList) {
            Node n = visit(decCtx);
            if (n != null) decorators.add(n);
        }
        symbolTable.free();
        return new FunctionDefNode(line, funcName, parameters, body, decorators);
    }


    @Override
    public Node visitDecoratorCall(python_parser.DecoratorCallContext ctx) {
        int line = ctx.start.getLine();
        Node func = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());
        for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
            func = new DotNode(line, func, new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
        }
        List<Node> args = new ArrayList<>();
        for (python_parser.ExprContext e : ctx.expr()) {
            Node arg = visit(e);
            if (arg != null) args.add(arg);
        }
        for (var child : ctx.children) {
            if (child instanceof python_parser.ArrayContext) {
                Node n = visit(child);
                if (n != null) args.add(n);
            }
        }
        return new FunctionCallNode(line, func, args);
    }


    @Override
    public Node visitWhileState(python_parser.WhileStateContext ctx) {
        int line = ctx.start.getLine();
        python_parser.WhileStmtContext whileCtx = ctx.whileStmt();
        Node condition = visit(whileCtx.expr());
        List<Node> body = new ArrayList<>();
        for (var st : whileCtx.stat()) {
            Node n = visit(st);
            if (n != null) body.add(n);
        }
        List<Node> elseBody = new ArrayList<>();
        if (whileCtx.ELSE() != null) {
            int elseIndex = whileCtx.ELSE().getSymbol().getTokenIndex();
            for (var st : whileCtx.stat()) {
                if (st.start.getTokenIndex() > elseIndex) {
                    Node n = visit(st);
                    if (n != null) elseBody.add(n);
                }
            }
        }
        return new WhileNode(line, condition, body, elseBody.isEmpty() ? null : elseBody);
    }
    private List<Node> collectStatsBetween(List<python_parser.StatContext> stats, int startTokenIndex, int endTokenIndex) {
        List<Node> result = new ArrayList<>();
        for (var st : stats) {
            int idx = st.start.getTokenIndex();
            if (idx > startTokenIndex && idx < endTokenIndex) {
                Node n = visit(st);
                if (n != null) result.add(n);
            }
        }
        return result;
    }

    @Override
    public Node visitListStmt(python_parser.ListStmtContext ctx) {
        int line = ctx.start.getLine();
        IdentifierNode target = new IdentifierNode(line, ctx.IDENTIFIER().getText());
        SymbolRow row = symbolTable.insert(target.getName());
        row.setType("list");
        row.setLine(line);
        List<Node> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add(visit(e));
        }
        CollectionNode list = new CollectionNode(line, CollectionType.LIST, elements);
        return new AssignNode(line, target, list);
    }

    @Override
    public Node visitTuplesStmt(python_parser.TuplesStmtContext ctx) {
        int line = ctx.start.getLine();
        IdentifierNode target = new IdentifierNode(line, ctx.IDENTIFIER().getText());
        SymbolRow row = symbolTable.insert(target.getName());
        row.setType("tuple");
        row.setLine(line);

        List<Node> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add(visit(e));
        }

        CollectionNode tuple = new CollectionNode(line, CollectionType.TUPLE, elements);
        return new AssignNode(line, target, tuple);
    }

    @Override
    public Node visitSetStmt(python_parser.SetStmtContext ctx) {
        int line = ctx.start.getLine();
        IdentifierNode target = new IdentifierNode(line, ctx.IDENTIFIER().getText());
        SymbolRow row = symbolTable.insert(target.getName());
        row.setType("set");
        row.setLine(line);

        List<Node> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add(visit(e));
        }

        CollectionNode set = new CollectionNode(line, CollectionType.SET, elements);
        return new AssignNode(line, target, set);
    }

    @Override
    public Node visitDictStmt(python_parser.DictStmtContext ctx) {
        int line = ctx.start.getLine();
        IdentifierNode target = new IdentifierNode(line, ctx.IDENTIFIER().getText());
        SymbolRow row = symbolTable.insert(target.getName());
        row.setType("dict");
        row.setLine(line);
        List<Node> keys = new ArrayList<>();
        List<Node> values = new ArrayList<>();
        for (int i = 0; i < ctx.expr().size(); i += 2) {
            keys.add(visit(ctx.expr(i)));
            values.add(visit(ctx.expr(i + 1)));
        }

        CollectionNode dict = new CollectionNode(line, keys, values);
        return new AssignNode(line, target, dict);
    }

    @Override
    public Node visitArray(python_parser.ArrayContext ctx) {
        if (ctx.listStmt() != null) return visit(ctx.listStmt());
        if (ctx.tuplesStmt() != null) return visit(ctx.tuplesStmt());
        if (ctx.setStmt() != null) return visit(ctx.setStmt());
        if (ctx.dictStmt() != null) return visit(ctx.dictStmt());
        if (ctx.RANGE() != null) {
            int line = ctx.start.getLine();
            List<Node> args = new ArrayList<>();
            for (var e : ctx.expr()) args.add(visit(e));
            return new FunctionCallNode(line, new IdentifierNode(line, "range"), args);
        }

        return null;
    }


    @Override
    public Node visitIfStat(python_parser.IfStatContext ctx) {
        int line = ctx.start.getLine();
        python_parser.IfStmtContext ifCtx = ctx.ifStmt();
        Node condition;
        List<Node> thenBody;
        List<Node> elifConditions = new ArrayList<>();
        List<List<Node>> elifBodies = new ArrayList<>();
        List<Node> elseBody = new ArrayList<>();
        List<python_parser.StatContext> allStats = ifCtx.stat();
        if (ifCtx.IN() == null && ifCtx.NULL() == null) {
            condition = visit(ifCtx.expr(0));

            int ifStart = ifCtx.IF().getSymbol().getTokenIndex();
            int ifEnd = ifCtx.ELIF().isEmpty() ? (ifCtx.ELSE() != null ? ifCtx.ELSE().getSymbol().getTokenIndex() : Integer.MAX_VALUE) : ifCtx.ELIF(0).getSymbol().getTokenIndex();
            thenBody = collectStatsBetween(allStats, ifStart, ifEnd);
            for (int i = 0; i < ifCtx.ELIF().size(); i++) {
                elifConditions.add(visit(ifCtx.expr(i + 1)));

                int elifStart = ifCtx.ELIF(i).getSymbol().getTokenIndex();
                int elifEnd = (i + 1 < ifCtx.ELIF().size()) ? ifCtx.ELIF(i + 1).getSymbol().getTokenIndex() : (ifCtx.ELSE() != null ? ifCtx.ELSE().getSymbol().getTokenIndex() : Integer.MAX_VALUE);

                elifBodies.add(collectStatsBetween(allStats, elifStart, elifEnd));
            }

            if (ifCtx.ELSE() != null) {
                int elseStart = ifCtx.ELSE().getSymbol().getTokenIndex();
                elseBody = collectStatsBetween(allStats, elseStart, Integer.MAX_VALUE);
            }
        }

        else if (ifCtx.IN() != null) {
            Node left = visit(ifCtx.expr(0));
            Node right = visit(ifCtx.expr(1));
            Node inExpr = new BinaryOpNode(line, left, "in", right);
            condition = (ifCtx.NOT() != null) ? new UnaryOpNode(line, "not", inExpr) : inExpr;

            thenBody = collectStatsBetween(allStats, ifCtx.IF().getSymbol().getTokenIndex(), Integer.MAX_VALUE);
        }else {
            condition = ifCtx.expr().isEmpty() ? new NullNode(line) : new BinaryOpNode(line, visit(ifCtx.expr(0)), "is", new NullNode(line));

            thenBody = collectStatsBetween(allStats, ifCtx.IF().getSymbol().getTokenIndex(), Integer.MAX_VALUE);
        }
        return new IfNode(line, condition, thenBody, elifConditions.isEmpty() ? null : elifConditions, elifBodies.isEmpty() ? null : elifBodies, elseBody.isEmpty() ? null : elseBody);
    }

    @Override
    public Node visitForState(python_parser.ForStateContext ctx) {
        int line = ctx.start.getLine();
        var forCtx = ctx.forStmt();

        SymbolRow it = symbolTable.insert(forCtx.IDENTIFIER().getText());
        it.setType("iterator");
        it.setLine(line);

        Node iterator = new IdentifierNode(line, forCtx.IDENTIFIER().getText());
        Node iterable = visit(forCtx.expr());

        List<Node> body = new ArrayList<>();
        for (var stmtCtx : forCtx.stat()) {
            Node n = visit(stmtCtx);
            if (n != null) body.add(n);
        }

        return new ForNode(line, iterator, iterable, body, new ArrayList<>());
    }

    @Override
    public Node visitPrintStat(python_parser.PrintStatContext ctx) {
        int line = ctx.start.getLine();
        python_parser.PrintStmtContext printCtx = ctx.printStmt();
        List<Node> arguments = new ArrayList<>();
        for (var printStat : printCtx.expr())
            arguments.add(visit(printStat));
        return new PrintNode(line, arguments);
    }

    @Override
    public Node visitReturnStat(python_parser.ReturnStatContext ctx) {
        int line = ctx.start.getLine();
        python_parser.ReturnStmtContext returnCtx = ctx.returnStmt();
        List<Node> body = new ArrayList<>();
        for (var expRe : returnCtx.expr())
            body.add(visit(expRe));
        return new ReturnNode(line, body);
    }

    @Override
    public Node visitBreakState(python_parser.BreakStateContext ctx) {
        int line = ctx.start.getLine();
        return new BreakNode(line);
    }

    @Override
    public Node visitContinue(python_parser.ContinueContext ctx) {
        int line = ctx.start.getLine();
        return new ContinueNode(line);
    }

    @Override
    public Node visitAssignment(python_parser.AssignmentContext ctx) {
        int line = ctx.start.getLine();
        SymbolRow row = symbolTable.insert(ctx.IDENTIFIER(0).getText());
        row.setType("variable");
        row.setLine(line);

        Node value = visit(ctx.expr(ctx.expr().size() - 1));


        Node target;
        if (ctx.DOT().isEmpty() && ctx.LSB().isEmpty()) {
            target = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());
        } else if (!ctx.DOT().isEmpty()) {
            target = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());
            for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                target = new DotNode(line, target, new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
            }
        } else { // array access
            Node array = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());
            List<Node> indices = new ArrayList<>();
            for (int i = 0; i < ctx.expr().size() - 1; i++) indices.add(visit(ctx.expr(i)));
            target = new ArrayAccessNode(line, array, indices);
        }

        return new AssignNode(line, target, value);
    }


    @Override
    public Node visitExpr(python_parser.ExprContext ctx) {
        int line = ctx.start.getLine();
        if (ctx.getChildCount() == 1) return visit(ctx.getChild(0));
        Node node = visit(ctx.getChild(0));
        for (int i = 1; i < ctx.getChildCount(); i += 2) {
            String op = ctx.getChild(i).getText();
            Node right = visit(ctx.getChild(i + 1));
            node = new BinaryOpNode(line, node, op, right);
        }
        return node;
    }

    @Override
    public Node visitTerm(python_parser.TermContext ctx) {
        int line = ctx.start.getLine();
        if (ctx.getChildCount() == 1) return visit(ctx.getChild(0));
        Node left = visit(ctx.getChild(0));
        String op = ctx.getChild(1).getText();
        Node right = visit(ctx.getChild(2));
        return new BinaryOpNode(line, left, op, right);
    }

    @Override
    public Node visitFactor(python_parser.FactorContext ctx) {
        int line = ctx.start.getLine();
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        }
        if (ctx.NOT() != null) {
            return new UnaryOpNode(line, "not", visit(ctx.factor()));
        }
        if (ctx.NUMBER() != null) {
            String text = ctx.NUMBER().getText();
            if (text.contains(".") || text.contains("e") || text.contains("E")) {
                return new NumberNode(line, Double.parseDouble(text));
            }
            return new NumberNode(line, Integer.parseInt(text));
        }
        if (ctx.STRING() != null)
            return new StringNode(line, ctx.STRING().getText().substring(1, ctx.STRING().getText().length() - 1));
        if (ctx.TRUE() != null) return new BooleanNode(line, true);
        if (ctx.FALSE() != null) return new BooleanNode(line, false);
        if (ctx.NULL() != null) return new NullNode(line);
        if (ctx.LKB() != null) {
            if (!ctx.COL().isEmpty()) {
                List<Node> keys = new ArrayList<>();
                List<Node> values = new ArrayList<>();

                for (int i = 0; i < ctx.expr().size(); i += 2) {
                    keys.add(visit(ctx.expr(i)));
                    values.add(visit(ctx.expr(i + 1)));
                }
                return new CollectionNode(line, keys, values);
            }
            List<Node> elements = new ArrayList<>();
            for (var e : ctx.expr()) elements.add(visit(e));
            return new CollectionNode(line, CollectionType.SET, elements);
        }
        if (ctx.OPEN_B() != null && ctx.IDENTIFIER().isEmpty()) {
            if (ctx.COMMA().size() > 0) {
                List<Node> elements = new ArrayList<>();
                for (var e : ctx.expr()) elements.add(visit(e));
                return new CollectionNode(line, CollectionType.TUPLE, elements);
            }
            return visit(ctx.expr(0));
        }
        if (ctx.LSB() != null && ctx.IDENTIFIER().isEmpty()) {
            List<Node> elements = new ArrayList<>();
            for (var e : ctx.expr()) elements.add(visit(e));
            return new CollectionNode(line, CollectionType.LIST, elements);
        }
        if (!ctx.IDENTIFIER().isEmpty() && ctx.OPEN_B() != null) {

            Node func = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());

            for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                func = new DotNode(line, func, new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
            }
            List<Node> args = new ArrayList<>();
            for (var e : ctx.expr()) {
                args.add(visit(e));
            }

            return new FunctionCallNode(line, func, args);
        }
        if (!ctx.IDENTIFIER().isEmpty()) {

            Node node = new IdentifierNode(line, ctx.IDENTIFIER(0).getText());

            for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                node = new DotNode(line, node, new IdentifierNode(line, ctx.IDENTIFIER(i).getText()));
            }

            int exprIndex = 0;
            for (int i = 0; i < ctx.LSB().size(); i++) {
                List<Node> indices = new ArrayList<>();
                indices.add(visit(ctx.expr(exprIndex++)));
                node = new ArrayAccessNode(line, node, indices);
            }

            return node;
        }
        return visitChildren(ctx);
    }
}