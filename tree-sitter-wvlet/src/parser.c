#include <tree_sitter/parser.h>

#if defined(__GNUC__) || defined(__clang__)
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wmissing-field-initializers"
#endif

#define LANGUAGE_VERSION 14
#define STATE_COUNT 38
#define LARGE_STATE_COUNT 2
#define SYMBOL_COUNT 33
#define ALIAS_COUNT 0
#define TOKEN_COUNT 16
#define EXTERNAL_TOKEN_COUNT 0
#define FIELD_COUNT 0
#define MAX_ALIAS_SEQUENCE_LENGTH 3
#define PRODUCTION_ID_COUNT 1

enum {
  anon_sym_for = 1,
  anon_sym_COMMA = 2,
  anon_sym_in = 3,
  anon_sym_where = 4,
  anon_sym_return = 5,
  anon_sym_EQ = 6,
  anon_sym_LT = 7,
  anon_sym_GT = 8,
  anon_sym_LT_EQ = 9,
  anon_sym_GT_EQ = 10,
  anon_sym_BANG_EQ = 11,
  anon_sym_DOT = 12,
  anon_sym_COLON = 13,
  sym_identifier = 14,
  sym_number = 15,
  sym_source_file = 16,
  sym_flowr_expr = 17,
  sym_for_expr = 18,
  sym_for_item = 19,
  sym_table_expr = 20,
  sym_where_expr = 21,
  sym_return_expr = 22,
  sym_return_item = 23,
  sym__expression = 24,
  sym__conditional_expression = 25,
  sym_comparison_expression = 26,
  sym_comparisonOperator = 27,
  sym_qname = 28,
  sym__identifier = 29,
  aux_sym_for_expr_repeat1 = 30,
  aux_sym_return_expr_repeat1 = 31,
  aux_sym_qname_repeat1 = 32,
};

static const char * const ts_symbol_names[] = {
  [ts_builtin_sym_end] = "end",
  [anon_sym_for] = "for",
  [anon_sym_COMMA] = ",",
  [anon_sym_in] = "in",
  [anon_sym_where] = "where",
  [anon_sym_return] = "return",
  [anon_sym_EQ] = "=",
  [anon_sym_LT] = "<",
  [anon_sym_GT] = ">",
  [anon_sym_LT_EQ] = "<=",
  [anon_sym_GT_EQ] = ">=",
  [anon_sym_BANG_EQ] = "!=",
  [anon_sym_DOT] = ".",
  [anon_sym_COLON] = ":",
  [sym_identifier] = "identifier",
  [sym_number] = "number",
  [sym_source_file] = "source_file",
  [sym_flowr_expr] = "flowr_expr",
  [sym_for_expr] = "for_expr",
  [sym_for_item] = "for_item",
  [sym_table_expr] = "table_expr",
  [sym_where_expr] = "where_expr",
  [sym_return_expr] = "return_expr",
  [sym_return_item] = "return_item",
  [sym__expression] = "_expression",
  [sym__conditional_expression] = "_conditional_expression",
  [sym_comparison_expression] = "comparison_expression",
  [sym_comparisonOperator] = "comparisonOperator",
  [sym_qname] = "qname",
  [sym__identifier] = "_identifier",
  [aux_sym_for_expr_repeat1] = "for_expr_repeat1",
  [aux_sym_return_expr_repeat1] = "return_expr_repeat1",
  [aux_sym_qname_repeat1] = "qname_repeat1",
};

static const TSSymbol ts_symbol_map[] = {
  [ts_builtin_sym_end] = ts_builtin_sym_end,
  [anon_sym_for] = anon_sym_for,
  [anon_sym_COMMA] = anon_sym_COMMA,
  [anon_sym_in] = anon_sym_in,
  [anon_sym_where] = anon_sym_where,
  [anon_sym_return] = anon_sym_return,
  [anon_sym_EQ] = anon_sym_EQ,
  [anon_sym_LT] = anon_sym_LT,
  [anon_sym_GT] = anon_sym_GT,
  [anon_sym_LT_EQ] = anon_sym_LT_EQ,
  [anon_sym_GT_EQ] = anon_sym_GT_EQ,
  [anon_sym_BANG_EQ] = anon_sym_BANG_EQ,
  [anon_sym_DOT] = anon_sym_DOT,
  [anon_sym_COLON] = anon_sym_COLON,
  [sym_identifier] = sym_identifier,
  [sym_number] = sym_number,
  [sym_source_file] = sym_source_file,
  [sym_flowr_expr] = sym_flowr_expr,
  [sym_for_expr] = sym_for_expr,
  [sym_for_item] = sym_for_item,
  [sym_table_expr] = sym_table_expr,
  [sym_where_expr] = sym_where_expr,
  [sym_return_expr] = sym_return_expr,
  [sym_return_item] = sym_return_item,
  [sym__expression] = sym__expression,
  [sym__conditional_expression] = sym__conditional_expression,
  [sym_comparison_expression] = sym_comparison_expression,
  [sym_comparisonOperator] = sym_comparisonOperator,
  [sym_qname] = sym_qname,
  [sym__identifier] = sym__identifier,
  [aux_sym_for_expr_repeat1] = aux_sym_for_expr_repeat1,
  [aux_sym_return_expr_repeat1] = aux_sym_return_expr_repeat1,
  [aux_sym_qname_repeat1] = aux_sym_qname_repeat1,
};

static const TSSymbolMetadata ts_symbol_metadata[] = {
  [ts_builtin_sym_end] = {
    .visible = false,
    .named = true,
  },
  [anon_sym_for] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_COMMA] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_in] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_where] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_return] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_EQ] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_LT] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_GT] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_LT_EQ] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_GT_EQ] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_BANG_EQ] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_DOT] = {
    .visible = true,
    .named = false,
  },
  [anon_sym_COLON] = {
    .visible = true,
    .named = false,
  },
  [sym_identifier] = {
    .visible = true,
    .named = true,
  },
  [sym_number] = {
    .visible = true,
    .named = true,
  },
  [sym_source_file] = {
    .visible = true,
    .named = true,
  },
  [sym_flowr_expr] = {
    .visible = true,
    .named = true,
  },
  [sym_for_expr] = {
    .visible = true,
    .named = true,
  },
  [sym_for_item] = {
    .visible = true,
    .named = true,
  },
  [sym_table_expr] = {
    .visible = true,
    .named = true,
  },
  [sym_where_expr] = {
    .visible = true,
    .named = true,
  },
  [sym_return_expr] = {
    .visible = true,
    .named = true,
  },
  [sym_return_item] = {
    .visible = true,
    .named = true,
  },
  [sym__expression] = {
    .visible = false,
    .named = true,
  },
  [sym__conditional_expression] = {
    .visible = false,
    .named = true,
  },
  [sym_comparison_expression] = {
    .visible = true,
    .named = true,
  },
  [sym_comparisonOperator] = {
    .visible = true,
    .named = true,
  },
  [sym_qname] = {
    .visible = true,
    .named = true,
  },
  [sym__identifier] = {
    .visible = false,
    .named = true,
  },
  [aux_sym_for_expr_repeat1] = {
    .visible = false,
    .named = false,
  },
  [aux_sym_return_expr_repeat1] = {
    .visible = false,
    .named = false,
  },
  [aux_sym_qname_repeat1] = {
    .visible = false,
    .named = false,
  },
};

static const TSSymbol ts_alias_sequences[PRODUCTION_ID_COUNT][MAX_ALIAS_SEQUENCE_LENGTH] = {
  [0] = {0},
};

static const uint16_t ts_non_terminal_alias_map[] = {
  0,
};

static const TSStateId ts_primary_state_ids[STATE_COUNT] = {
  [0] = 0,
  [1] = 1,
  [2] = 2,
  [3] = 3,
  [4] = 4,
  [5] = 5,
  [6] = 6,
  [7] = 7,
  [8] = 8,
  [9] = 9,
  [10] = 10,
  [11] = 11,
  [12] = 12,
  [13] = 13,
  [14] = 14,
  [15] = 15,
  [16] = 16,
  [17] = 17,
  [18] = 18,
  [19] = 19,
  [20] = 20,
  [21] = 21,
  [22] = 22,
  [23] = 23,
  [24] = 24,
  [25] = 25,
  [26] = 26,
  [27] = 27,
  [28] = 28,
  [29] = 29,
  [30] = 30,
  [31] = 31,
  [32] = 32,
  [33] = 33,
  [34] = 34,
  [35] = 35,
  [36] = 36,
  [37] = 37,
};

static bool ts_lex(TSLexer *lexer, TSStateId state) {
  START_LEXER();
  eof = lexer->eof(lexer);
  switch (state) {
    case 0:
      if (eof) ADVANCE(15);
      if (lookahead == '!') ADVANCE(1);
      if (lookahead == ',') ADVANCE(17);
      if (lookahead == '.') ADVANCE(27);
      if (lookahead == ':') ADVANCE(28);
      if (lookahead == '<') ADVANCE(22);
      if (lookahead == '=') ADVANCE(21);
      if (lookahead == '>') ADVANCE(23);
      if (lookahead == 'f') ADVANCE(8);
      if (lookahead == 'i') ADVANCE(6);
      if (lookahead == 'r') ADVANCE(2);
      if (lookahead == 'w') ADVANCE(5);
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(0)
      if (('0' <= lookahead && lookahead <= '9')) ADVANCE(30);
      END_STATE();
    case 1:
      if (lookahead == '=') ADVANCE(26);
      END_STATE();
    case 2:
      if (lookahead == 'e') ADVANCE(12);
      END_STATE();
    case 3:
      if (lookahead == 'e') ADVANCE(19);
      END_STATE();
    case 4:
      if (lookahead == 'e') ADVANCE(11);
      END_STATE();
    case 5:
      if (lookahead == 'h') ADVANCE(4);
      END_STATE();
    case 6:
      if (lookahead == 'n') ADVANCE(18);
      END_STATE();
    case 7:
      if (lookahead == 'n') ADVANCE(20);
      END_STATE();
    case 8:
      if (lookahead == 'o') ADVANCE(9);
      END_STATE();
    case 9:
      if (lookahead == 'r') ADVANCE(16);
      END_STATE();
    case 10:
      if (lookahead == 'r') ADVANCE(7);
      END_STATE();
    case 11:
      if (lookahead == 'r') ADVANCE(3);
      END_STATE();
    case 12:
      if (lookahead == 't') ADVANCE(13);
      END_STATE();
    case 13:
      if (lookahead == 'u') ADVANCE(10);
      END_STATE();
    case 14:
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(14)
      if (('0' <= lookahead && lookahead <= '9')) ADVANCE(30);
      if (('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(29);
      END_STATE();
    case 15:
      ACCEPT_TOKEN(ts_builtin_sym_end);
      END_STATE();
    case 16:
      ACCEPT_TOKEN(anon_sym_for);
      END_STATE();
    case 17:
      ACCEPT_TOKEN(anon_sym_COMMA);
      END_STATE();
    case 18:
      ACCEPT_TOKEN(anon_sym_in);
      END_STATE();
    case 19:
      ACCEPT_TOKEN(anon_sym_where);
      END_STATE();
    case 20:
      ACCEPT_TOKEN(anon_sym_return);
      END_STATE();
    case 21:
      ACCEPT_TOKEN(anon_sym_EQ);
      END_STATE();
    case 22:
      ACCEPT_TOKEN(anon_sym_LT);
      if (lookahead == '=') ADVANCE(24);
      END_STATE();
    case 23:
      ACCEPT_TOKEN(anon_sym_GT);
      if (lookahead == '=') ADVANCE(25);
      END_STATE();
    case 24:
      ACCEPT_TOKEN(anon_sym_LT_EQ);
      END_STATE();
    case 25:
      ACCEPT_TOKEN(anon_sym_GT_EQ);
      END_STATE();
    case 26:
      ACCEPT_TOKEN(anon_sym_BANG_EQ);
      END_STATE();
    case 27:
      ACCEPT_TOKEN(anon_sym_DOT);
      END_STATE();
    case 28:
      ACCEPT_TOKEN(anon_sym_COLON);
      END_STATE();
    case 29:
      ACCEPT_TOKEN(sym_identifier);
      if (('0' <= lookahead && lookahead <= '9') ||
          ('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(29);
      END_STATE();
    case 30:
      ACCEPT_TOKEN(sym_number);
      if (('0' <= lookahead && lookahead <= '9') ||
          lookahead == '_') ADVANCE(30);
      END_STATE();
    default:
      return false;
  }
}

static const TSLexMode ts_lex_modes[STATE_COUNT] = {
  [0] = {.lex_state = 0},
  [1] = {.lex_state = 0},
  [2] = {.lex_state = 0},
  [3] = {.lex_state = 0},
  [4] = {.lex_state = 0},
  [5] = {.lex_state = 0},
  [6] = {.lex_state = 0},
  [7] = {.lex_state = 0},
  [8] = {.lex_state = 14},
  [9] = {.lex_state = 14},
  [10] = {.lex_state = 0},
  [11] = {.lex_state = 14},
  [12] = {.lex_state = 14},
  [13] = {.lex_state = 0},
  [14] = {.lex_state = 0},
  [15] = {.lex_state = 0},
  [16] = {.lex_state = 0},
  [17] = {.lex_state = 0},
  [18] = {.lex_state = 0},
  [19] = {.lex_state = 0},
  [20] = {.lex_state = 0},
  [21] = {.lex_state = 0},
  [22] = {.lex_state = 0},
  [23] = {.lex_state = 0},
  [24] = {.lex_state = 14},
  [25] = {.lex_state = 14},
  [26] = {.lex_state = 0},
  [27] = {.lex_state = 14},
  [28] = {.lex_state = 14},
  [29] = {.lex_state = 14},
  [30] = {.lex_state = 0},
  [31] = {.lex_state = 0},
  [32] = {.lex_state = 0},
  [33] = {.lex_state = 0},
  [34] = {.lex_state = 0},
  [35] = {.lex_state = 0},
  [36] = {.lex_state = 0},
  [37] = {.lex_state = 0},
};

static const uint16_t ts_parse_table[LARGE_STATE_COUNT][SYMBOL_COUNT] = {
  [0] = {
    [ts_builtin_sym_end] = ACTIONS(1),
    [anon_sym_for] = ACTIONS(1),
    [anon_sym_COMMA] = ACTIONS(1),
    [anon_sym_in] = ACTIONS(1),
    [anon_sym_where] = ACTIONS(1),
    [anon_sym_return] = ACTIONS(1),
    [anon_sym_EQ] = ACTIONS(1),
    [anon_sym_LT] = ACTIONS(1),
    [anon_sym_GT] = ACTIONS(1),
    [anon_sym_LT_EQ] = ACTIONS(1),
    [anon_sym_GT_EQ] = ACTIONS(1),
    [anon_sym_BANG_EQ] = ACTIONS(1),
    [anon_sym_DOT] = ACTIONS(1),
    [anon_sym_COLON] = ACTIONS(1),
    [sym_number] = ACTIONS(1),
  },
  [1] = {
    [sym_source_file] = STATE(34),
    [sym_flowr_expr] = STATE(37),
    [sym_for_expr] = STATE(14),
    [sym_where_expr] = STATE(23),
    [sym_return_expr] = STATE(32),
    [anon_sym_for] = ACTIONS(3),
    [anon_sym_where] = ACTIONS(5),
    [anon_sym_return] = ACTIONS(7),
  },
};

static const uint16_t ts_small_parse_table[] = {
  [0] = 4,
    ACTIONS(13), 1,
      anon_sym_DOT,
    STATE(3), 1,
      aux_sym_qname_repeat1,
    ACTIONS(11), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(9), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [20] = 4,
    ACTIONS(19), 1,
      anon_sym_DOT,
    STATE(3), 1,
      aux_sym_qname_repeat1,
    ACTIONS(17), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(15), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [40] = 3,
    STATE(11), 1,
      sym_comparisonOperator,
    ACTIONS(24), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(22), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [57] = 3,
    ACTIONS(30), 1,
      anon_sym_DOT,
    ACTIONS(28), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(26), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [74] = 2,
    ACTIONS(17), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(15), 8,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
      anon_sym_DOT,
  [89] = 4,
    STATE(11), 1,
      sym_comparisonOperator,
    ACTIONS(32), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
    ACTIONS(36), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(34), 4,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [107] = 5,
    ACTIONS(38), 1,
      sym_identifier,
    ACTIONS(40), 1,
      sym_number,
    STATE(19), 1,
      sym_return_item,
    STATE(30), 1,
      sym__identifier,
    STATE(7), 4,
      sym__expression,
      sym__conditional_expression,
      sym_comparison_expression,
      sym_qname,
  [126] = 5,
    ACTIONS(38), 1,
      sym_identifier,
    ACTIONS(40), 1,
      sym_number,
    STATE(30), 1,
      sym__identifier,
    STATE(31), 1,
      sym_return_item,
    STATE(7), 4,
      sym__expression,
      sym__conditional_expression,
      sym_comparison_expression,
      sym_qname,
  [145] = 4,
    ACTIONS(42), 1,
      anon_sym_return,
    STATE(11), 1,
      sym_comparisonOperator,
    ACTIONS(36), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(34), 4,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [162] = 4,
    ACTIONS(38), 1,
      sym_identifier,
    ACTIONS(44), 1,
      sym_number,
    STATE(30), 1,
      sym__identifier,
    STATE(4), 4,
      sym__expression,
      sym__conditional_expression,
      sym_comparison_expression,
      sym_qname,
  [178] = 4,
    ACTIONS(38), 1,
      sym_identifier,
    ACTIONS(46), 1,
      sym_number,
    STATE(30), 1,
      sym__identifier,
    STATE(10), 4,
      sym__expression,
      sym__conditional_expression,
      sym_comparison_expression,
      sym_qname,
  [194] = 3,
    ACTIONS(48), 1,
      anon_sym_COMMA,
    STATE(13), 1,
      aux_sym_for_expr_repeat1,
    ACTIONS(51), 2,
      anon_sym_where,
      anon_sym_return,
  [205] = 4,
    ACTIONS(5), 1,
      anon_sym_where,
    ACTIONS(7), 1,
      anon_sym_return,
    STATE(26), 1,
      sym_where_expr,
    STATE(35), 1,
      sym_return_expr,
  [218] = 3,
    ACTIONS(53), 1,
      anon_sym_COMMA,
    STATE(16), 1,
      aux_sym_for_expr_repeat1,
    ACTIONS(55), 2,
      anon_sym_where,
      anon_sym_return,
  [229] = 3,
    ACTIONS(53), 1,
      anon_sym_COMMA,
    STATE(13), 1,
      aux_sym_for_expr_repeat1,
    ACTIONS(57), 2,
      anon_sym_where,
      anon_sym_return,
  [240] = 3,
    ACTIONS(59), 1,
      ts_builtin_sym_end,
    ACTIONS(61), 1,
      anon_sym_COMMA,
    STATE(17), 1,
      aux_sym_return_expr_repeat1,
  [250] = 1,
    ACTIONS(64), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [256] = 3,
    ACTIONS(66), 1,
      ts_builtin_sym_end,
    ACTIONS(68), 1,
      anon_sym_COMMA,
    STATE(22), 1,
      aux_sym_return_expr_repeat1,
  [266] = 1,
    ACTIONS(70), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [272] = 1,
    ACTIONS(51), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [278] = 3,
    ACTIONS(68), 1,
      anon_sym_COMMA,
    ACTIONS(72), 1,
      ts_builtin_sym_end,
    STATE(17), 1,
      aux_sym_return_expr_repeat1,
  [288] = 2,
    ACTIONS(7), 1,
      anon_sym_return,
    STATE(35), 1,
      sym_return_expr,
  [295] = 2,
    ACTIONS(74), 1,
      sym_identifier,
    STATE(6), 1,
      sym__identifier,
  [302] = 2,
    ACTIONS(76), 1,
      sym_identifier,
    STATE(21), 1,
      sym_for_item,
  [309] = 2,
    ACTIONS(7), 1,
      anon_sym_return,
    STATE(33), 1,
      sym_return_expr,
  [316] = 1,
    ACTIONS(78), 2,
      sym_identifier,
      sym_number,
  [321] = 2,
    ACTIONS(80), 1,
      sym_identifier,
    STATE(18), 1,
      sym_table_expr,
  [328] = 2,
    ACTIONS(76), 1,
      sym_identifier,
    STATE(15), 1,
      sym_for_item,
  [335] = 2,
    ACTIONS(13), 1,
      anon_sym_DOT,
    STATE(2), 1,
      aux_sym_qname_repeat1,
  [342] = 1,
    ACTIONS(59), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
  [347] = 1,
    ACTIONS(82), 1,
      ts_builtin_sym_end,
  [351] = 1,
    ACTIONS(84), 1,
      ts_builtin_sym_end,
  [355] = 1,
    ACTIONS(86), 1,
      ts_builtin_sym_end,
  [359] = 1,
    ACTIONS(88), 1,
      ts_builtin_sym_end,
  [363] = 1,
    ACTIONS(90), 1,
      anon_sym_in,
  [367] = 1,
    ACTIONS(92), 1,
      ts_builtin_sym_end,
};

static const uint32_t ts_small_parse_table_map[] = {
  [SMALL_STATE(2)] = 0,
  [SMALL_STATE(3)] = 20,
  [SMALL_STATE(4)] = 40,
  [SMALL_STATE(5)] = 57,
  [SMALL_STATE(6)] = 74,
  [SMALL_STATE(7)] = 89,
  [SMALL_STATE(8)] = 107,
  [SMALL_STATE(9)] = 126,
  [SMALL_STATE(10)] = 145,
  [SMALL_STATE(11)] = 162,
  [SMALL_STATE(12)] = 178,
  [SMALL_STATE(13)] = 194,
  [SMALL_STATE(14)] = 205,
  [SMALL_STATE(15)] = 218,
  [SMALL_STATE(16)] = 229,
  [SMALL_STATE(17)] = 240,
  [SMALL_STATE(18)] = 250,
  [SMALL_STATE(19)] = 256,
  [SMALL_STATE(20)] = 266,
  [SMALL_STATE(21)] = 272,
  [SMALL_STATE(22)] = 278,
  [SMALL_STATE(23)] = 288,
  [SMALL_STATE(24)] = 295,
  [SMALL_STATE(25)] = 302,
  [SMALL_STATE(26)] = 309,
  [SMALL_STATE(27)] = 316,
  [SMALL_STATE(28)] = 321,
  [SMALL_STATE(29)] = 328,
  [SMALL_STATE(30)] = 335,
  [SMALL_STATE(31)] = 342,
  [SMALL_STATE(32)] = 347,
  [SMALL_STATE(33)] = 351,
  [SMALL_STATE(34)] = 355,
  [SMALL_STATE(35)] = 359,
  [SMALL_STATE(36)] = 363,
  [SMALL_STATE(37)] = 367,
};

static const TSParseActionEntry ts_parse_actions[] = {
  [0] = {.entry = {.count = 0, .reusable = false}},
  [1] = {.entry = {.count = 1, .reusable = false}}, RECOVER(),
  [3] = {.entry = {.count = 1, .reusable = true}}, SHIFT(29),
  [5] = {.entry = {.count = 1, .reusable = true}}, SHIFT(12),
  [7] = {.entry = {.count = 1, .reusable = true}}, SHIFT(8),
  [9] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_qname, 2),
  [11] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym_qname, 2),
  [13] = {.entry = {.count = 1, .reusable = true}}, SHIFT(24),
  [15] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_qname_repeat1, 2),
  [17] = {.entry = {.count = 1, .reusable = false}}, REDUCE(aux_sym_qname_repeat1, 2),
  [19] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_qname_repeat1, 2), SHIFT_REPEAT(24),
  [22] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_comparison_expression, 3),
  [24] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym_comparison_expression, 3),
  [26] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym__expression, 1),
  [28] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym__expression, 1),
  [30] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym__identifier, 1),
  [32] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_item, 1),
  [34] = {.entry = {.count = 1, .reusable = true}}, SHIFT(27),
  [36] = {.entry = {.count = 1, .reusable = false}}, SHIFT(27),
  [38] = {.entry = {.count = 1, .reusable = true}}, SHIFT(5),
  [40] = {.entry = {.count = 1, .reusable = true}}, SHIFT(7),
  [42] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_where_expr, 2),
  [44] = {.entry = {.count = 1, .reusable = true}}, SHIFT(4),
  [46] = {.entry = {.count = 1, .reusable = true}}, SHIFT(10),
  [48] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2), SHIFT_REPEAT(25),
  [51] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2),
  [53] = {.entry = {.count = 1, .reusable = true}}, SHIFT(25),
  [55] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 2),
  [57] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 3),
  [59] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2),
  [61] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2), SHIFT_REPEAT(9),
  [64] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_item, 3),
  [66] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 2),
  [68] = {.entry = {.count = 1, .reusable = true}}, SHIFT(9),
  [70] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_table_expr, 1),
  [72] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 3),
  [74] = {.entry = {.count = 1, .reusable = true}}, SHIFT(6),
  [76] = {.entry = {.count = 1, .reusable = true}}, SHIFT(36),
  [78] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_comparisonOperator, 1),
  [80] = {.entry = {.count = 1, .reusable = true}}, SHIFT(20),
  [82] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 1),
  [84] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 3),
  [86] = {.entry = {.count = 1, .reusable = true}},  ACCEPT_INPUT(),
  [88] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 2),
  [90] = {.entry = {.count = 1, .reusable = true}}, SHIFT(28),
  [92] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_source_file, 1),
};

#ifdef __cplusplus
extern "C" {
#endif
#ifdef _WIN32
#define extern __declspec(dllexport)
#endif

extern const TSLanguage *tree_sitter_wvlet(void) {
  static const TSLanguage language = {
    .version = LANGUAGE_VERSION,
    .symbol_count = SYMBOL_COUNT,
    .alias_count = ALIAS_COUNT,
    .token_count = TOKEN_COUNT,
    .external_token_count = EXTERNAL_TOKEN_COUNT,
    .state_count = STATE_COUNT,
    .large_state_count = LARGE_STATE_COUNT,
    .production_id_count = PRODUCTION_ID_COUNT,
    .field_count = FIELD_COUNT,
    .max_alias_sequence_length = MAX_ALIAS_SEQUENCE_LENGTH,
    .parse_table = &ts_parse_table[0][0],
    .small_parse_table = ts_small_parse_table,
    .small_parse_table_map = ts_small_parse_table_map,
    .parse_actions = ts_parse_actions,
    .symbol_names = ts_symbol_names,
    .symbol_metadata = ts_symbol_metadata,
    .public_symbol_map = ts_symbol_map,
    .alias_map = ts_non_terminal_alias_map,
    .alias_sequences = &ts_alias_sequences[0][0],
    .lex_modes = ts_lex_modes,
    .lex_fn = ts_lex,
    .primary_state_ids = ts_primary_state_ids,
  };
  return &language;
}
#ifdef __cplusplus
}
#endif
