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

public final class CtlExistsExpr extends UnaryExpr<BoolType, BoolType> {

	private static final int HASH_SEED = 7994;

	private static final String OPERAND_LABEL = "E";

	private CtlExistsExpr(final Expr<BoolType> op) {
		super(op);
	}

	public static CtlExistsExpr of(final Expr<BoolType> op) {
		return new CtlExistsExpr(op);
	}

	public static CtlExistsExpr create(final Expr<?> op) {
		final Expr<BoolType> newOp = cast(op, Bool());
		return CtlExistsExpr.of(newOp);
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
	public CtlExistsExpr with(final Expr<BoolType> op) {
		if (op == getOp()) {
			return this;
		} else {
			return new CtlExistsExpr(op);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof CtlExistsExpr) {
			final CtlExistsExpr that = (CtlExistsExpr) obj;
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
