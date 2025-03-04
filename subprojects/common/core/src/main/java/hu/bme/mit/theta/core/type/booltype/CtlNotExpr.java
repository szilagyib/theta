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

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.UnaryExpr;

import static hu.bme.mit.theta.core.type.booltype.BoolExprs.Bool;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public final class CtlNotExpr extends UnaryExpr<BoolType, BoolType> {

	private static final int HASH_SEED = 128;

	private static final String OPERAND_LABEL = "Not";

	private CtlNotExpr(final Expr<BoolType> op) {
		super(op);
	}

	public static CtlNotExpr of(final Expr<BoolType> op) {
		return new CtlNotExpr(op);
	}

	public static CtlNotExpr create(final Expr<?> op) {
		final Expr<BoolType> newOp = cast(op, Bool());
		return CtlNotExpr.of(newOp);
	}

	@Override
	public BoolType getType() {
		return Bool();
	}

	@Override
	public BoolLitExpr eval(final Valuation val) {
		final BoolLitExpr opVal = (BoolLitExpr) getOp().eval(val);
		return Bool(!opVal.getValue());
	}

	@Override
	public CtlNotExpr with(final Expr<BoolType> op) {
		if (op == getOp()) {
			return this;
		} else {
			return new CtlNotExpr(op);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof CtlNotExpr) {
			final CtlNotExpr that = (CtlNotExpr) obj;
			return this.getOp().equals(that.getOp());
		} else {
			return false;
		}
	}

	@Override
	protected int getHashSeed() {
		return HASH_SEED;
	}

	@Override
	public String getOperatorLabel() {
		return OPERAND_LABEL;
	}

}
