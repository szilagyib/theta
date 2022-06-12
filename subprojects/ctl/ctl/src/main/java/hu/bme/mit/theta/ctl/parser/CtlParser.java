package hu.bme.mit.theta.ctl.parser;

import hu.bme.mit.theta.common.parser.LispLexer;
import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.ctl.CTL;

import java.io.Reader;

import static com.google.common.base.Preconditions.checkNotNull;

public final class CtlParser {

    private final LispParser parser;
    private final CtlInterpreter interpreter;

    public CtlParser(final Reader reader) {
        checkNotNull(reader);
        final LispLexer lexer = new LispLexer(reader);
        parser = new LispParser(lexer);
        final Env env = new Env();
        interpreter = new CtlInterpreter(env);
    }

    public CTL ctl() {
        final SExpr sexpr = parser.sexpr();
        final CTL ctl = interpreter.ctl(sexpr);
        return ctl;
    }
}
