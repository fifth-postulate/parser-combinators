package nl.fifthpostulate.parser.demo;

import nl.fifthpostulate.parser.Parser;

import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.base.Succeed.succeed;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Lazy.lazy;
import static nl.fifthpostulate.parser.combinator.Map.map;
import static nl.fifthpostulate.parser.combinator.Or.or;
import static nl.fifthpostulate.parser.utility.Utility.keepLeft;
import static nl.fifthpostulate.parser.utility.Utility.keepRight;

/* We would like to parse a string that represents a balanced set of parenthesis
 * "", "()", "(())", "()()", "((()))", "(()())", "(())()", "()(())", "()()()"
 *
 */
