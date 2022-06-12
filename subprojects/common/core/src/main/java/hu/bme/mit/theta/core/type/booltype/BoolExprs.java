/*
 *  Copyright 2017 Budapest University of Technology and Economics
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package hu.bme.mit.theta.core.type.booltype;

import com.google.common.collect.ImmutableList;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.type.Expr;

public final class BoolExprs {

	private BoolExprs() {
	}

	public static BoolType Bool() {
		return BoolType.getInstance();
	}

	public static BoolLitExpr Bool(final boolean value) {
		return BoolLitExpr.of(value);
	}

	public static TrueExpr True() {
		return TrueExpr.getInstance();
	}

	public static FalseExpr False() {
		return FalseExpr.getInstance();
	}

	public static NotExpr Not(final Expr<BoolType> op) {
		return NotExpr.of(op);
	}

	public static ImplyExpr Imply(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return ImplyExpr.of(leftOp, rightOp);
	}

	public static IffExpr Iff(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return IffExpr.of(leftOp, rightOp);
	}

	public static XorExpr Xor(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return XorExpr.of(leftOp, rightOp);
	}

	public static AndExpr And(final Iterable<? extends Expr<BoolType>> ops) {
		return AndExpr.of(ops);
	}

	public static OrExpr Or(final Iterable<? extends Expr<BoolType>> ops) {
		return OrExpr.of(ops);
	}

	public static ForallExpr Forall(final Iterable<? extends ParamDecl<?>> paramDecls, final Expr<BoolType> op) {
		return ForallExpr.of(paramDecls, op);
	}

	public static ExistsExpr Exists(final Iterable<? extends ParamDecl<?>> paramDecls, final Expr<BoolType> op) {
		return ExistsExpr.of(paramDecls, op);
	}

	/*
	 * Convenience methods
	 */

	public static AndExpr And(final Expr<BoolType> op1, final Expr<BoolType> op2) {
		return AndExpr.of(ImmutableList.of(op1, op2));
	}

	public static AndExpr And(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3) {
		return AndExpr.of(ImmutableList.of(op1, op2, op3));
	}

	public static AndExpr And(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3,
							  final Expr<BoolType> op4) {
		return AndExpr.of(ImmutableList.of(op1, op2, op3, op4));
	}

	public static AndExpr And(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3,
							  final Expr<BoolType> op4, final Expr<BoolType> op5) {
		return AndExpr.of(ImmutableList.of(op1, op2, op3, op4, op5));
	}

	////

	public static OrExpr Or(final Expr<BoolType> op1, final Expr<BoolType> op2) {
		return OrExpr.of(ImmutableList.of(op1, op2));
	}

	public static OrExpr Or(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3) {
		return OrExpr.of(ImmutableList.of(op1, op2, op3));
	}

	public static OrExpr Or(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3,
							final Expr<BoolType> op4) {
		return OrExpr.of(ImmutableList.of(op1, op2, op3, op4));
	}

	public static OrExpr Or(final Expr<BoolType> op1, final Expr<BoolType> op2, final Expr<BoolType> op3,
							final Expr<BoolType> op4, final Expr<BoolType> op5) {
		return OrExpr.of(ImmutableList.of(op1, op2, op3, op4, op5));
	}

	// CTL state formula operators

	public static CtlNotExpr CtlNot(final Expr<BoolType> op) {
		return CtlNotExpr.of(op);
	}

	public static CtlExistsExpr CtlExists(final Expr<BoolType> op) {
		return CtlExistsExpr.of(op);
	}

	public static CtlForallExpr CtlForall(final Expr<BoolType> op) {
		return CtlForallExpr.of(op);
	}

	public static CtlAndExpr CtlAnd(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return CtlAndExpr.of(leftOp, rightOp);
	}

	public static CtlOrExpr CtlOr(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return CtlOrExpr.of(leftOp, rightOp);
	}

	public static CtlImplyExpr CtlImply(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return CtlImplyExpr.of(leftOp, rightOp);
	}

	public static CtlEqExpr CtlEq(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return CtlEqExpr.of(leftOp, rightOp);
	}

	// CTL path formula operators

	public static GloballyExpr Globally(final Expr<BoolType> op) {
		return GloballyExpr.of(op);
	}

	public static FinallyExpr Finally(final Expr<BoolType> op) {
		return FinallyExpr.of(op);
	}

	public static NextExpr Next(final Expr<BoolType> op) {
		return NextExpr.of(op);
	}

	public static UntilExpr Until(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return UntilExpr.of(leftOp, rightOp);
	}

	public static ReleaseExpr Release(final Expr<BoolType> leftOp, final Expr<BoolType> rightOp) {
		return ReleaseExpr.of(leftOp, rightOp);
	}
}
