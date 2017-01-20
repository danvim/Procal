package org.bychan.core.basic;

import org.bychan.core.dynamic.Language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by alext on 2016-12-02.
 */
public class ReplBuilder<N> {


    private final Language<N> language;

    private Repl.ParsingFunction<N> parsingFunction;

    private Repl.EvaluationFunction<N> evaluationFunction;
    private BufferedReader in;
    private BufferedWriter out;


    public ReplBuilder( final Language<N> language) {
        this.language = language;
        withIn(System.in).withOut(System.out);
        parsingFunction = LexParser::tryParse;
        evaluationFunction = Repl::reflectionInvokeEvaluate;
    }

    private ReplBuilder<N> withIn(InputStream in) {
        return withIn(new BufferedReader(new InputStreamReader(in)));
    }

    public ReplBuilder<N> withIn(BufferedReader in) {
        this.in = in;
        return this;
    }

    public ReplBuilder<N> withOut(BufferedWriter out) {
        this.out = out;
        return this;
    }

    public ReplBuilder<N> withParsingFunction(Repl.ParsingFunction<N> parsingFunction) {
        this.parsingFunction = parsingFunction;
        return this;
    }

    public ReplBuilder<N> withEvaluationFunction(Repl.EvaluationFunction<N> evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
        return this;
    }

    public Repl<N> build() {
        return new Repl<>(language, in, out, parsingFunction, evaluationFunction);
    }

    public ReplBuilder<N> withOut(OutputStream out) {
        return withOut(new BufferedWriter(new OutputStreamWriter(out)));
    }
}
