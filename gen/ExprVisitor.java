// Generated from grammar/Expr.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#vars_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars_decl(ExprParser.Vars_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#funcs_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncs_decl(ExprParser.Funcs_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ExprParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStat(ExprParser.AssignStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondStat(ExprParser.CondStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cycleStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCycleStat(ExprParser.CycleStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallStat(ExprParser.FuncCallStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressiao}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressiao(ExprParser.ExpressiaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStat(ExprParser.PrintStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyStat}
	 * labeled alternative in {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStat(ExprParser.EmptyStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(ExprParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ExprParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#cycle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCycle(ExprParser.CycleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#f_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_call(ExprParser.F_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#printExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(ExprParser.PrintExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termExpr}
	 * labeled alternative in {@link ExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermExpr(ExprParser.TermExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code aritExpr}
	 * labeled alternative in {@link ExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAritExpr(ExprParser.AritExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link ExprParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(ExprParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code factorExpr}
	 * labeled alternative in {@link ExprParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactorExpr(ExprParser.FactorExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(ExprParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#relExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelExpr(ExprParser.RelExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link ExprParser#cte}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntConst(ExprParser.IntConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatConst}
	 * labeled alternative in {@link ExprParser#cte}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatConst(ExprParser.FloatConstContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(ExprParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(ExprParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ExprParser.TypeContext ctx);
}