const PREC = {
  comment: 1,
}

module.exports = grammar({
  name: 'wvlet',

  extras: $ => [$.comment, /\s/],

  rules: {
    source_file: $ => choice($.flowr_expr),

    flowr_expr: $ => seq(
        optional($.for_expr),
        optional($.where_expr),
        $.return_expr
    ),

    for_expr: $ => seq(
        'for',
        sep1(',', $.for_item)
    ),

    for_item: $ => seq(
        $.identifier,
        'in',
        $.table_expr
    ),

    table_expr: $ => seq(
        $.identifier
    ),

    where_expr: $ => seq(
        'where',
        $._expression
    ),

    return_expr: $ => seq(
        'return',
        sep1(',', $.return_item)
    ),

    return_item: $ => choice(
        $._expression
    ),

    _expression: $ => choice(
        $.qname,
        $.identifier,
        $.number,
        $._conditional_expression
    ),

    _conditional_expression: $ => choice(
        $.comparison_expression
    ),

    comparison_expression: $ => prec.left(1,
        seq(
            $._expression,
            $.comparisonOperator,
            $._expression
        )
    ),

    comparisonOperator: $ => choice(
        '=',
        '<',
        '>',
        '<=',
        '>=',
        '!='
    ),

    qname : $ => seq($._identifier, repeat1(seq('.', $._identifier))),

    named_identifier: $ => seq(
        $.identifier,
        ':',
        $._expression
    ),

    // An identifier is a sequence of one or more letters, numbers, or underscores.
    identifier: $ => /[a-zA-Z_][a-zA-Z0-9_]*/,

    _identifier: $ => $.identifier,

    // Number strings are sequences of digits, possibly separated by underscores.
    number: $ => /\d[\d_]*/,

    comment: $ => seq(token("//"), choice($._comment_text)),
    _comment_text: $ => token(prec(PREC.comment, /.*/)),
  }
});

function sep1(delimiter, rule) {
  return seq(rule, repeat(seq(delimiter, rule)));
}
