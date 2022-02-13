package nl.fifthpostulate.parser.example;


import nl.fifthpostulate.parser.Parser;

import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.base.Succeed.succeed;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Lazy.lazy;
import static nl.fifthpostulate.parser.combinator.Map.map;
import static nl.fifthpostulate.parser.combinator.Or.or;
import static nl.fifthpostulate.parser.example.Leaf.LEAF;
import static nl.fifthpostulate.parser.utility.Utility.pack;

public sealed interface Tree permits Node, Leaf {
    static Parser<Tree> parser() {
        return or(
                map(and(pack(character('('), lazy(Tree::parser), character(')')), lazy(Tree::parser)), p -> node(p.first(), p.second())),
                succeed(leaf()));
    }

    static Tree node(Tree left, Tree right) {
        return new Node(left, right);
    }

    static Tree leaf() {
        return LEAF;
    }
}

record Node(Tree left, Tree right) implements Tree {
}

enum Leaf implements Tree {
    LEAF
}
