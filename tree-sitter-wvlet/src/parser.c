#include <tree_sitter/parser.h>

#if defined(__GNUC__) || defined(__clang__)
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wmissing-field-initializers"
#endif

#define LANGUAGE_VERSION 14
#define STATE_COUNT 43
#define LARGE_STATE_COUNT 2
#define SYMBOL_COUNT 36
#define ALIAS_COUNT 0
#define TOKEN_COUNT 18
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
  anon_sym_SLASH_SLASH = 16,
  sym__comment_text = 17,
  sym_source_file = 18,
  sym_flowr_expr = 19,
  sym_for_expr = 20,
  sym_for_item = 21,
  sym_table_expr = 22,
  sym_where_expr = 23,
  sym_return_expr = 24,
  sym_return_item = 25,
  sym__expression = 26,
  sym__conditional_expression = 27,
  sym_comparison_expression = 28,
  sym_comparisonOperator = 29,
  sym_qname = 30,
  sym__identifier = 31,
  sym_comment = 32,
  aux_sym_for_expr_repeat1 = 33,
  aux_sym_return_expr_repeat1 = 34,
  aux_sym_qname_repeat1 = 35,
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
  [anon_sym_SLASH_SLASH] = "//",
  [sym__comment_text] = "_comment_text",
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
  [sym_comment] = "comment",
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
  [anon_sym_SLASH_SLASH] = anon_sym_SLASH_SLASH,
  [sym__comment_text] = sym__comment_text,
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
  [sym_comment] = sym_comment,
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
  [anon_sym_SLASH_SLASH] = {
    .visible = true,
    .named = false,
  },
  [sym__comment_text] = {
    .visible = false,
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
  [sym_comment] = {
    .visible = true,
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
  [38] = 38,
  [39] = 39,
  [40] = 40,
  [41] = 41,
  [42] = 42,
};

static bool ts_lex(TSLexer *lexer, TSStateId state) {
  START_LEXER();
  eof = lexer->eof(lexer);
  switch (state) {
    case 0:
      if (eof) ADVANCE(16);
      if (lookahead == '!') ADVANCE(3);
      if (lookahead == ',') ADVANCE(18);
      if (lookahead == '.') ADVANCE(28);
      if (lookahead == '/') ADVANCE(2);
      if (lookahead == ':') ADVANCE(29);
      if (lookahead == '<') ADVANCE(23);
      if (lookahead == '=') ADVANCE(22);
      if (lookahead == '>') ADVANCE(24);
      if (lookahead == 'f') ADVANCE(10);
      if (lookahead == 'i') ADVANCE(8);
      if (lookahead == 'r') ADVANCE(4);
      if (lookahead == 'w') ADVANCE(7);
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(0)
      if (('0' <= lookahead && lookahead <= '9')) ADVANCE(31);
      END_STATE();
    case 1:
      if (lookahead == '/') ADVANCE(2);
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(1)
      if (('0' <= lookahead && lookahead <= '9')) ADVANCE(31);
      if (('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(30);
      END_STATE();
    case 2:
      if (lookahead == '/') ADVANCE(32);
      END_STATE();
    case 3:
      if (lookahead == '=') ADVANCE(27);
      END_STATE();
    case 4:
      if (lookahead == 'e') ADVANCE(14);
      END_STATE();
    case 5:
      if (lookahead == 'e') ADVANCE(20);
      END_STATE();
    case 6:
      if (lookahead == 'e') ADVANCE(13);
      END_STATE();
    case 7:
      if (lookahead == 'h') ADVANCE(6);
      END_STATE();
    case 8:
      if (lookahead == 'n') ADVANCE(19);
      END_STATE();
    case 9:
      if (lookahead == 'n') ADVANCE(21);
      END_STATE();
    case 10:
      if (lookahead == 'o') ADVANCE(11);
      END_STATE();
    case 11:
      if (lookahead == 'r') ADVANCE(17);
      END_STATE();
    case 12:
      if (lookahead == 'r') ADVANCE(9);
      END_STATE();
    case 13:
      if (lookahead == 'r') ADVANCE(5);
      END_STATE();
    case 14:
      if (lookahead == 't') ADVANCE(15);
      END_STATE();
    case 15:
      if (lookahead == 'u') ADVANCE(12);
      END_STATE();
    case 16:
      ACCEPT_TOKEN(ts_builtin_sym_end);
      END_STATE();
    case 17:
      ACCEPT_TOKEN(anon_sym_for);
      END_STATE();
    case 18:
      ACCEPT_TOKEN(anon_sym_COMMA);
      END_STATE();
    case 19:
      ACCEPT_TOKEN(anon_sym_in);
      END_STATE();
    case 20:
      ACCEPT_TOKEN(anon_sym_where);
      END_STATE();
    case 21:
      ACCEPT_TOKEN(anon_sym_return);
      END_STATE();
    case 22:
      ACCEPT_TOKEN(anon_sym_EQ);
      END_STATE();
    case 23:
      ACCEPT_TOKEN(anon_sym_LT);
      if (lookahead == '=') ADVANCE(25);
      END_STATE();
    case 24:
      ACCEPT_TOKEN(anon_sym_GT);
      if (lookahead == '=') ADVANCE(26);
      END_STATE();
    case 25:
      ACCEPT_TOKEN(anon_sym_LT_EQ);
      END_STATE();
    case 26:
      ACCEPT_TOKEN(anon_sym_GT_EQ);
      END_STATE();
    case 27:
      ACCEPT_TOKEN(anon_sym_BANG_EQ);
      END_STATE();
    case 28:
      ACCEPT_TOKEN(anon_sym_DOT);
      END_STATE();
    case 29:
      ACCEPT_TOKEN(anon_sym_COLON);
      END_STATE();
    case 30:
      ACCEPT_TOKEN(sym_identifier);
      if (('0' <= lookahead && lookahead <= '9') ||
          ('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(30);
      END_STATE();
    case 31:
      ACCEPT_TOKEN(sym_number);
      if (('0' <= lookahead && lookahead <= '9') ||
          lookahead == '_') ADVANCE(31);
      END_STATE();
    case 32:
      ACCEPT_TOKEN(anon_sym_SLASH_SLASH);
      END_STATE();
    case 33:
      ACCEPT_TOKEN(sym__comment_text);
      if (lookahead == '/') ADVANCE(34);
      if (lookahead == '\t' ||
          lookahead == '\r' ||
          lookahead == ' ') ADVANCE(33);
      if (lookahead != 0 &&
          lookahead != '\n') ADVANCE(35);
      END_STATE();
    case 34:
      ACCEPT_TOKEN(sym__comment_text);
      if (lookahead == '/') ADVANCE(35);
      if (lookahead != 0 &&
          lookahead != '\n') ADVANCE(35);
      END_STATE();
    case 35:
      ACCEPT_TOKEN(sym__comment_text);
      if (lookahead != 0 &&
          lookahead != '\n') ADVANCE(35);
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
  [8] = {.lex_state = 0},
  [9] = {.lex_state = 0},
  [10] = {.lex_state = 0},
  [11] = {.lex_state = 1},
  [12] = {.lex_state = 1},
  [13] = {.lex_state = 0},
  [14] = {.lex_state = 1},
  [15] = {.lex_state = 1},
  [16] = {.lex_state = 0},
  [17] = {.lex_state = 0},
  [18] = {.lex_state = 0},
  [19] = {.lex_state = 0},
  [20] = {.lex_state = 0},
  [21] = {.lex_state = 0},
  [22] = {.lex_state = 0},
  [23] = {.lex_state = 0},
  [24] = {.lex_state = 0},
  [25] = {.lex_state = 0},
  [26] = {.lex_state = 0},
  [27] = {.lex_state = 1},
  [28] = {.lex_state = 1},
  [29] = {.lex_state = 0},
  [30] = {.lex_state = 1},
  [31] = {.lex_state = 1},
  [32] = {.lex_state = 0},
  [33] = {.lex_state = 1},
  [34] = {.lex_state = 0},
  [35] = {.lex_state = 33},
  [36] = {.lex_state = 0},
  [37] = {.lex_state = 0},
  [38] = {.lex_state = 0},
  [39] = {.lex_state = 0},
  [40] = {.lex_state = 0},
  [41] = {.lex_state = 0},
  [42] = {(TSStateId)(-1)},
};

static const uint16_t ts_parse_table[LARGE_STATE_COUNT][SYMBOL_COUNT] = {
  [0] = {
    [sym_comment] = STATE(0),
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
    [anon_sym_SLASH_SLASH] = ACTIONS(3),
  },
  [1] = {
    [sym_source_file] = STATE(39),
    [sym_flowr_expr] = STATE(36),
    [sym_for_expr] = STATE(18),
    [sym_where_expr] = STATE(29),
    [sym_return_expr] = STATE(41),
    [sym_comment] = STATE(1),
    [anon_sym_for] = ACTIONS(5),
    [anon_sym_where] = ACTIONS(7),
    [anon_sym_return] = ACTIONS(9),
    [anon_sym_SLASH_SLASH] = ACTIONS(3),
  },
};

static const uint16_t ts_small_parse_table[] = {
  [0] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(15), 1,
      anon_sym_DOT,
    ACTIONS(13), 2,
      anon_sym_LT,
      anon_sym_GT,
    STATE(2), 2,
      sym_comment,
      aux_sym_qname_repeat1,
    ACTIONS(11), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [24] = 6,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(22), 1,
      anon_sym_DOT,
    STATE(2), 1,
      aux_sym_qname_repeat1,
    STATE(3), 1,
      sym_comment,
    ACTIONS(20), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(18), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [50] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(4), 1,
      sym_comment,
    ACTIONS(13), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(11), 8,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
      anon_sym_DOT,
  [71] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(5), 1,
      sym_comment,
    ACTIONS(26), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(24), 8,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
      anon_sym_DOT,
  [92] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(6), 1,
      sym_comment,
    STATE(14), 1,
      sym_comparisonOperator,
    ACTIONS(30), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(28), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [115] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(24), 1,
      anon_sym_DOT,
    STATE(7), 1,
      sym_comment,
    ACTIONS(34), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(32), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [138] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(8), 1,
      sym_comment,
    ACTIONS(34), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(32), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [158] = 6,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(9), 1,
      sym_comment,
    STATE(14), 1,
      sym_comparisonOperator,
    ACTIONS(36), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
    ACTIONS(40), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(38), 4,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [182] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(10), 1,
      sym_comment,
    ACTIONS(44), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(42), 7,
      ts_builtin_sym_end,
      anon_sym_COMMA,
      anon_sym_return,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [202] = 9,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(46), 1,
      sym_identifier,
    ACTIONS(48), 1,
      sym_number,
    STATE(9), 1,
      sym__expression,
    STATE(10), 1,
      sym_comparison_expression,
    STATE(11), 1,
      sym_comment,
    STATE(26), 1,
      sym__identifier,
    STATE(34), 1,
      sym_return_item,
    STATE(8), 2,
      sym__conditional_expression,
      sym_qname,
  [231] = 9,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(46), 1,
      sym_identifier,
    ACTIONS(48), 1,
      sym_number,
    STATE(9), 1,
      sym__expression,
    STATE(10), 1,
      sym_comparison_expression,
    STATE(12), 1,
      sym_comment,
    STATE(21), 1,
      sym_return_item,
    STATE(26), 1,
      sym__identifier,
    STATE(8), 2,
      sym__conditional_expression,
      sym_qname,
  [260] = 6,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(50), 1,
      anon_sym_return,
    STATE(13), 1,
      sym_comment,
    STATE(14), 1,
      sym_comparisonOperator,
    ACTIONS(40), 2,
      anon_sym_LT,
      anon_sym_GT,
    ACTIONS(38), 4,
      anon_sym_EQ,
      anon_sym_LT_EQ,
      anon_sym_GT_EQ,
      anon_sym_BANG_EQ,
  [283] = 8,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(46), 1,
      sym_identifier,
    ACTIONS(48), 1,
      sym_number,
    STATE(6), 1,
      sym__expression,
    STATE(10), 1,
      sym_comparison_expression,
    STATE(14), 1,
      sym_comment,
    STATE(26), 1,
      sym__identifier,
    STATE(8), 2,
      sym__conditional_expression,
      sym_qname,
  [309] = 8,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(46), 1,
      sym_identifier,
    ACTIONS(48), 1,
      sym_number,
    STATE(10), 1,
      sym_comparison_expression,
    STATE(13), 1,
      sym__expression,
    STATE(15), 1,
      sym_comment,
    STATE(26), 1,
      sym__identifier,
    STATE(8), 2,
      sym__conditional_expression,
      sym_qname,
  [335] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(52), 1,
      anon_sym_COMMA,
    ACTIONS(55), 2,
      anon_sym_where,
      anon_sym_return,
    STATE(16), 2,
      sym_comment,
      aux_sym_for_expr_repeat1,
  [350] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(57), 1,
      anon_sym_COMMA,
    STATE(17), 1,
      sym_comment,
    STATE(19), 1,
      aux_sym_for_expr_repeat1,
    ACTIONS(59), 2,
      anon_sym_where,
      anon_sym_return,
  [367] = 6,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(7), 1,
      anon_sym_where,
    ACTIONS(9), 1,
      anon_sym_return,
    STATE(18), 1,
      sym_comment,
    STATE(32), 1,
      sym_where_expr,
    STATE(38), 1,
      sym_return_expr,
  [386] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(57), 1,
      anon_sym_COMMA,
    STATE(16), 1,
      aux_sym_for_expr_repeat1,
    STATE(19), 1,
      sym_comment,
    ACTIONS(61), 2,
      anon_sym_where,
      anon_sym_return,
  [403] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(63), 1,
      ts_builtin_sym_end,
    ACTIONS(65), 1,
      anon_sym_COMMA,
    STATE(20), 2,
      sym_comment,
      aux_sym_return_expr_repeat1,
  [417] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(68), 1,
      ts_builtin_sym_end,
    ACTIONS(70), 1,
      anon_sym_COMMA,
    STATE(21), 1,
      sym_comment,
    STATE(25), 1,
      aux_sym_return_expr_repeat1,
  [433] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(22), 1,
      sym_comment,
    ACTIONS(55), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [445] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(23), 1,
      sym_comment,
    ACTIONS(72), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [457] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(24), 1,
      sym_comment,
    ACTIONS(74), 3,
      anon_sym_COMMA,
      anon_sym_where,
      anon_sym_return,
  [469] = 5,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(70), 1,
      anon_sym_COMMA,
    ACTIONS(76), 1,
      ts_builtin_sym_end,
    STATE(20), 1,
      aux_sym_return_expr_repeat1,
    STATE(25), 1,
      sym_comment,
  [485] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(22), 1,
      anon_sym_DOT,
    STATE(3), 1,
      aux_sym_qname_repeat1,
    STATE(26), 1,
      sym_comment,
  [498] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(27), 1,
      sym_comment,
    ACTIONS(78), 2,
      sym_identifier,
      sym_number,
  [509] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(80), 1,
      sym_identifier,
    STATE(4), 1,
      sym__identifier,
    STATE(28), 1,
      sym_comment,
  [522] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(9), 1,
      anon_sym_return,
    STATE(29), 1,
      sym_comment,
    STATE(38), 1,
      sym_return_expr,
  [535] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(82), 1,
      sym_identifier,
    STATE(22), 1,
      sym_for_item,
    STATE(30), 1,
      sym_comment,
  [548] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(84), 1,
      sym_identifier,
    STATE(23), 1,
      sym_table_expr,
    STATE(31), 1,
      sym_comment,
  [561] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(9), 1,
      anon_sym_return,
    STATE(32), 1,
      sym_comment,
    STATE(37), 1,
      sym_return_expr,
  [574] = 4,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(82), 1,
      sym_identifier,
    STATE(17), 1,
      sym_for_item,
    STATE(33), 1,
      sym_comment,
  [587] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    STATE(34), 1,
      sym_comment,
    ACTIONS(63), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
  [598] = 3,
    ACTIONS(86), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(88), 1,
      sym__comment_text,
    STATE(35), 1,
      sym_comment,
  [608] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(90), 1,
      ts_builtin_sym_end,
    STATE(36), 1,
      sym_comment,
  [618] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(92), 1,
      ts_builtin_sym_end,
    STATE(37), 1,
      sym_comment,
  [628] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(94), 1,
      ts_builtin_sym_end,
    STATE(38), 1,
      sym_comment,
  [638] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(96), 1,
      ts_builtin_sym_end,
    STATE(39), 1,
      sym_comment,
  [648] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(98), 1,
      anon_sym_in,
    STATE(40), 1,
      sym_comment,
  [658] = 3,
    ACTIONS(3), 1,
      anon_sym_SLASH_SLASH,
    ACTIONS(100), 1,
      ts_builtin_sym_end,
    STATE(41), 1,
      sym_comment,
  [668] = 1,
    ACTIONS(102), 1,
      ts_builtin_sym_end,
};

static const uint32_t ts_small_parse_table_map[] = {
  [SMALL_STATE(2)] = 0,
  [SMALL_STATE(3)] = 24,
  [SMALL_STATE(4)] = 50,
  [SMALL_STATE(5)] = 71,
  [SMALL_STATE(6)] = 92,
  [SMALL_STATE(7)] = 115,
  [SMALL_STATE(8)] = 138,
  [SMALL_STATE(9)] = 158,
  [SMALL_STATE(10)] = 182,
  [SMALL_STATE(11)] = 202,
  [SMALL_STATE(12)] = 231,
  [SMALL_STATE(13)] = 260,
  [SMALL_STATE(14)] = 283,
  [SMALL_STATE(15)] = 309,
  [SMALL_STATE(16)] = 335,
  [SMALL_STATE(17)] = 350,
  [SMALL_STATE(18)] = 367,
  [SMALL_STATE(19)] = 386,
  [SMALL_STATE(20)] = 403,
  [SMALL_STATE(21)] = 417,
  [SMALL_STATE(22)] = 433,
  [SMALL_STATE(23)] = 445,
  [SMALL_STATE(24)] = 457,
  [SMALL_STATE(25)] = 469,
  [SMALL_STATE(26)] = 485,
  [SMALL_STATE(27)] = 498,
  [SMALL_STATE(28)] = 509,
  [SMALL_STATE(29)] = 522,
  [SMALL_STATE(30)] = 535,
  [SMALL_STATE(31)] = 548,
  [SMALL_STATE(32)] = 561,
  [SMALL_STATE(33)] = 574,
  [SMALL_STATE(34)] = 587,
  [SMALL_STATE(35)] = 598,
  [SMALL_STATE(36)] = 608,
  [SMALL_STATE(37)] = 618,
  [SMALL_STATE(38)] = 628,
  [SMALL_STATE(39)] = 638,
  [SMALL_STATE(40)] = 648,
  [SMALL_STATE(41)] = 658,
  [SMALL_STATE(42)] = 668,
};

static const TSParseActionEntry ts_parse_actions[] = {
  [0] = {.entry = {.count = 0, .reusable = false}},
  [1] = {.entry = {.count = 1, .reusable = false}}, RECOVER(),
  [3] = {.entry = {.count = 1, .reusable = true}}, SHIFT(35),
  [5] = {.entry = {.count = 1, .reusable = true}}, SHIFT(33),
  [7] = {.entry = {.count = 1, .reusable = true}}, SHIFT(15),
  [9] = {.entry = {.count = 1, .reusable = true}}, SHIFT(12),
  [11] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_qname_repeat1, 2),
  [13] = {.entry = {.count = 1, .reusable = false}}, REDUCE(aux_sym_qname_repeat1, 2),
  [15] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_qname_repeat1, 2), SHIFT_REPEAT(28),
  [18] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_qname, 2),
  [20] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym_qname, 2),
  [22] = {.entry = {.count = 1, .reusable = true}}, SHIFT(28),
  [24] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym__identifier, 1),
  [26] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym__identifier, 1),
  [28] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_comparison_expression, 3),
  [30] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym_comparison_expression, 3),
  [32] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym__expression, 1),
  [34] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym__expression, 1),
  [36] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_item, 1),
  [38] = {.entry = {.count = 1, .reusable = true}}, SHIFT(27),
  [40] = {.entry = {.count = 1, .reusable = false}}, SHIFT(27),
  [42] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym__conditional_expression, 1),
  [44] = {.entry = {.count = 1, .reusable = false}}, REDUCE(sym__conditional_expression, 1),
  [46] = {.entry = {.count = 1, .reusable = true}}, SHIFT(7),
  [48] = {.entry = {.count = 1, .reusable = true}}, SHIFT(8),
  [50] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_where_expr, 2),
  [52] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2), SHIFT_REPEAT(30),
  [55] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2),
  [57] = {.entry = {.count = 1, .reusable = true}}, SHIFT(30),
  [59] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 2),
  [61] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 3),
  [63] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2),
  [65] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2), SHIFT_REPEAT(11),
  [68] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 2),
  [70] = {.entry = {.count = 1, .reusable = true}}, SHIFT(11),
  [72] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_item, 3),
  [74] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_table_expr, 1),
  [76] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 3),
  [78] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_comparisonOperator, 1),
  [80] = {.entry = {.count = 1, .reusable = true}}, SHIFT(5),
  [82] = {.entry = {.count = 1, .reusable = true}}, SHIFT(40),
  [84] = {.entry = {.count = 1, .reusable = true}}, SHIFT(24),
  [86] = {.entry = {.count = 1, .reusable = false}}, SHIFT(35),
  [88] = {.entry = {.count = 1, .reusable = true}}, SHIFT(42),
  [90] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_source_file, 1),
  [92] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 3),
  [94] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 2),
  [96] = {.entry = {.count = 1, .reusable = true}},  ACCEPT_INPUT(),
  [98] = {.entry = {.count = 1, .reusable = true}}, SHIFT(31),
  [100] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 1),
  [102] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_comment, 2),
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
