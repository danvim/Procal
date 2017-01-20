package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.ParsingFailedException;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenParserCallback;

class UserParserCallbackImpl<N> implements UserParserCallback<N> {
    private final int leftBindingPower;

    private final TokenFinder<N> tokenFinder;

    private final N left;

    private final DynamicLexeme<N> lexeme;

    private final TokenParserCallback<N> parser;

    public UserParserCallbackImpl( final TokenDefinition<N> def,  TokenFinder<N> tokenFinder,  final TokenParserCallback<N> parser,  N left,  final DynamicLexeme<N> lexeme) {
        this.left = left;
        this.lexeme = lexeme;
        this.leftBindingPower = def.getLeftBindingPower();
        this.tokenFinder = tokenFinder;
        this.parser = parser;
    }



    @Override
    public N expression( N left, int leftBindingPower) {
        return parser.parseExpression(left, leftBindingPower);
    }


    @Override
    public N expression( N left) {
        return parser.parseExpression(left, leftBindingPower);
    }


    @Override
    public Lexeme<N> expectSingleLexeme( TokenKey tokenKey) {
        return swallow(tokenKey, parser);
    }


    private Lexeme<N> swallow( TokenKey tokenKey, TokenParserCallback<N> parser) {
        Token<N> token = tokenFinder.getToken(tokenKey);
        return parser.swallow(token);
    }

    @Override
    public boolean nextIs( TokenKey tokenKey) {
        Token<N> expectedToken = tokenFinder.getToken(tokenKey);
        return parser.peek().getToken().equals(expectedToken);
    }


    @Override
    public N parseSingleToken(N left,  TokenKey tokenKey) {
        Lexeme<N> lexeme = swallow(tokenKey, parser);
        return parser.nud(left, lexeme);
    }

    @Override
    public N nud() {
        return parser.nud(left, lexeme);
    }

    @Override
    public <S> S abort( String message) {
        throw ParsingFailedException.forFailedAfterLexing(message, parser);
    }


    @Override
    public Lexeme<N> next() {
        return parser.next();
    }
}
