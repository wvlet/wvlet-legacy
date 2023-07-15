#include <tree_sitter/parser.h>

#if defined(__GNUC__) || defined(__clang__)
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wmissing-field-initializers"
#endif

#define LANGUAGE_VERSION 14
#define STATE_COUNT 24
#define LARGE_STATE_COUNT 2
#define SYMBOL_COUNT 17
#define ALIAS_COUNT 0
#define TOKEN_COUNT 7
#define EXTERNAL_TOKEN_COUNT 0
#define FIELD_COUNT 0
#define MAX_ALIAS_SEQUENCE_LENGTH 3
#define PRODUCTION_ID_COUNT 1

enum {
  anon_sym_for = 1,
  anon_sym_COMMA = 2,
  anon_sym_in = 3,
  anon_sym_return = 4,
  sym_identifier = 5,
  sym_number = 6,
  sym_source_file = 7,
  sym_flowr_expr = 8,
  sym_for_expr = 9,
  sym_for_item = 10,
  sym_table_expr = 11,
  sym_return_expr = 12,
  sym_return_item = 13,
  sym__expression = 14,
  aux_sym_for_expr_repeat1 = 15,
  aux_sym_return_expr_repeat1 = 16,
};

static const char * const ts_symbol_names[] = {
  [ts_builtin_sym_end] = "end",
  [anon_sym_for] = "for",
  [anon_sym_COMMA] = ",",
  [anon_sym_in] = "in",
  [anon_sym_return] = "return",
  [sym_identifier] = "identifier",
  [sym_number] = "number",
  [sym_source_file] = "source_file",
  [sym_flowr_expr] = "flowr_expr",
  [sym_for_expr] = "for_expr",
  [sym_for_item] = "for_item",
  [sym_table_expr] = "table_expr",
  [sym_return_expr] = "return_expr",
  [sym_return_item] = "return_item",
  [sym__expression] = "_expression",
  [aux_sym_for_expr_repeat1] = "for_expr_repeat1",
  [aux_sym_return_expr_repeat1] = "return_expr_repeat1",
};

static const TSSymbol ts_symbol_map[] = {
  [ts_builtin_sym_end] = ts_builtin_sym_end,
  [anon_sym_for] = anon_sym_for,
  [anon_sym_COMMA] = anon_sym_COMMA,
  [anon_sym_in] = anon_sym_in,
  [anon_sym_return] = anon_sym_return,
  [sym_identifier] = sym_identifier,
  [sym_number] = sym_number,
  [sym_source_file] = sym_source_file,
  [sym_flowr_expr] = sym_flowr_expr,
  [sym_for_expr] = sym_for_expr,
  [sym_for_item] = sym_for_item,
  [sym_table_expr] = sym_table_expr,
  [sym_return_expr] = sym_return_expr,
  [sym_return_item] = sym_return_item,
  [sym__expression] = sym__expression,
  [aux_sym_for_expr_repeat1] = aux_sym_for_expr_repeat1,
  [aux_sym_return_expr_repeat1] = aux_sym_return_expr_repeat1,
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
  [anon_sym_return] = {
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
  [aux_sym_for_expr_repeat1] = {
    .visible = false,
    .named = false,
  },
  [aux_sym_return_expr_repeat1] = {
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
};

static bool ts_lex(TSLexer *lexer, TSStateId state) {
  START_LEXER();
  eof = lexer->eof(lexer);
  switch (state) {
    case 0:
      if (eof) ADVANCE(10);
      if (lookahead == ',') ADVANCE(12);
      if (lookahead == 'f') ADVANCE(4);
      if (lookahead == 'i') ADVANCE(2);
      if (lookahead == 'r') ADVANCE(1);
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(0)
      if (('0' <= lookahead && lookahead <= '9')) ADVANCE(16);
      END_STATE();
    case 1:
      if (lookahead == 'e') ADVANCE(7);
      END_STATE();
    case 2:
      if (lookahead == 'n') ADVANCE(13);
      END_STATE();
    case 3:
      if (lookahead == 'n') ADVANCE(14);
      END_STATE();
    case 4:
      if (lookahead == 'o') ADVANCE(5);
      END_STATE();
    case 5:
      if (lookahead == 'r') ADVANCE(11);
      END_STATE();
    case 6:
      if (lookahead == 'r') ADVANCE(3);
      END_STATE();
    case 7:
      if (lookahead == 't') ADVANCE(8);
      END_STATE();
    case 8:
      if (lookahead == 'u') ADVANCE(6);
      END_STATE();
    case 9:
      if (lookahead == '\t' ||
          lookahead == '\n' ||
          lookahead == '\r' ||
          lookahead == ' ') SKIP(9)
      if (('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(15);
      END_STATE();
    case 10:
      ACCEPT_TOKEN(ts_builtin_sym_end);
      END_STATE();
    case 11:
      ACCEPT_TOKEN(anon_sym_for);
      END_STATE();
    case 12:
      ACCEPT_TOKEN(anon_sym_COMMA);
      END_STATE();
    case 13:
      ACCEPT_TOKEN(anon_sym_in);
      END_STATE();
    case 14:
      ACCEPT_TOKEN(anon_sym_return);
      END_STATE();
    case 15:
      ACCEPT_TOKEN(sym_identifier);
      if (('0' <= lookahead && lookahead <= '9') ||
          ('A' <= lookahead && lookahead <= 'Z') ||
          lookahead == '_' ||
          ('a' <= lookahead && lookahead <= 'z')) ADVANCE(15);
      END_STATE();
    case 16:
      ACCEPT_TOKEN(sym_number);
      if (('0' <= lookahead && lookahead <= '9') ||
          lookahead == '_') ADVANCE(16);
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
  [4] = {.lex_state = 9},
  [5] = {.lex_state = 0},
  [6] = {.lex_state = 0},
  [7] = {.lex_state = 9},
  [8] = {.lex_state = 0},
  [9] = {.lex_state = 0},
  [10] = {.lex_state = 0},
  [11] = {.lex_state = 0},
  [12] = {.lex_state = 9},
  [13] = {.lex_state = 9},
  [14] = {.lex_state = 9},
  [15] = {.lex_state = 0},
  [16] = {.lex_state = 0},
  [17] = {.lex_state = 0},
  [18] = {.lex_state = 0},
  [19] = {.lex_state = 0},
  [20] = {.lex_state = 0},
  [21] = {.lex_state = 0},
  [22] = {.lex_state = 0},
  [23] = {.lex_state = 0},
};

static const uint16_t ts_parse_table[LARGE_STATE_COUNT][SYMBOL_COUNT] = {
  [0] = {
    [ts_builtin_sym_end] = ACTIONS(1),
    [anon_sym_for] = ACTIONS(1),
    [anon_sym_COMMA] = ACTIONS(1),
    [anon_sym_in] = ACTIONS(1),
    [anon_sym_return] = ACTIONS(1),
    [sym_number] = ACTIONS(1),
  },
  [1] = {
    [sym_source_file] = STATE(23),
    [sym_flowr_expr] = STATE(22),
    [sym_for_expr] = STATE(15),
    [sym_return_expr] = STATE(21),
    [anon_sym_for] = ACTIONS(3),
    [anon_sym_return] = ACTIONS(5),
  },
};

static const uint16_t ts_small_parse_table[] = {
  [0] = 3,
    ACTIONS(7), 1,
      ts_builtin_sym_end,
    ACTIONS(9), 1,
      anon_sym_COMMA,
    STATE(6), 1,
      aux_sym_return_expr_repeat1,
  [10] = 3,
    ACTIONS(11), 1,
      ts_builtin_sym_end,
    ACTIONS(13), 1,
      anon_sym_COMMA,
    STATE(3), 1,
      aux_sym_return_expr_repeat1,
  [20] = 3,
    ACTIONS(16), 1,
      sym_identifier,
    STATE(2), 1,
      sym_return_item,
    STATE(11), 1,
      sym__expression,
  [30] = 3,
    ACTIONS(18), 1,
      anon_sym_COMMA,
    ACTIONS(21), 1,
      anon_sym_return,
    STATE(5), 1,
      aux_sym_for_expr_repeat1,
  [40] = 3,
    ACTIONS(9), 1,
      anon_sym_COMMA,
    ACTIONS(23), 1,
      ts_builtin_sym_end,
    STATE(3), 1,
      aux_sym_return_expr_repeat1,
  [50] = 3,
    ACTIONS(16), 1,
      sym_identifier,
    STATE(11), 1,
      sym__expression,
    STATE(18), 1,
      sym_return_item,
  [60] = 3,
    ACTIONS(25), 1,
      anon_sym_COMMA,
    ACTIONS(27), 1,
      anon_sym_return,
    STATE(5), 1,
      aux_sym_for_expr_repeat1,
  [70] = 3,
    ACTIONS(25), 1,
      anon_sym_COMMA,
    ACTIONS(29), 1,
      anon_sym_return,
    STATE(8), 1,
      aux_sym_for_expr_repeat1,
  [80] = 1,
    ACTIONS(31), 2,
      anon_sym_COMMA,
      anon_sym_return,
  [85] = 1,
    ACTIONS(33), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
  [90] = 2,
    ACTIONS(35), 1,
      sym_identifier,
    STATE(16), 1,
      sym_table_expr,
  [97] = 2,
    ACTIONS(37), 1,
      sym_identifier,
    STATE(17), 1,
      sym_for_item,
  [104] = 2,
    ACTIONS(37), 1,
      sym_identifier,
    STATE(9), 1,
      sym_for_item,
  [111] = 2,
    ACTIONS(5), 1,
      anon_sym_return,
    STATE(19), 1,
      sym_return_expr,
  [118] = 1,
    ACTIONS(39), 2,
      anon_sym_COMMA,
      anon_sym_return,
  [123] = 1,
    ACTIONS(21), 2,
      anon_sym_COMMA,
      anon_sym_return,
  [128] = 1,
    ACTIONS(11), 2,
      ts_builtin_sym_end,
      anon_sym_COMMA,
  [133] = 1,
    ACTIONS(41), 1,
      ts_builtin_sym_end,
  [137] = 1,
    ACTIONS(43), 1,
      anon_sym_in,
  [141] = 1,
    ACTIONS(45), 1,
      ts_builtin_sym_end,
  [145] = 1,
    ACTIONS(47), 1,
      ts_builtin_sym_end,
  [149] = 1,
    ACTIONS(49), 1,
      ts_builtin_sym_end,
};

static const uint32_t ts_small_parse_table_map[] = {
  [SMALL_STATE(2)] = 0,
  [SMALL_STATE(3)] = 10,
  [SMALL_STATE(4)] = 20,
  [SMALL_STATE(5)] = 30,
  [SMALL_STATE(6)] = 40,
  [SMALL_STATE(7)] = 50,
  [SMALL_STATE(8)] = 60,
  [SMALL_STATE(9)] = 70,
  [SMALL_STATE(10)] = 80,
  [SMALL_STATE(11)] = 85,
  [SMALL_STATE(12)] = 90,
  [SMALL_STATE(13)] = 97,
  [SMALL_STATE(14)] = 104,
  [SMALL_STATE(15)] = 111,
  [SMALL_STATE(16)] = 118,
  [SMALL_STATE(17)] = 123,
  [SMALL_STATE(18)] = 128,
  [SMALL_STATE(19)] = 133,
  [SMALL_STATE(20)] = 137,
  [SMALL_STATE(21)] = 141,
  [SMALL_STATE(22)] = 145,
  [SMALL_STATE(23)] = 149,
};

static const TSParseActionEntry ts_parse_actions[] = {
  [0] = {.entry = {.count = 0, .reusable = false}},
  [1] = {.entry = {.count = 1, .reusable = false}}, RECOVER(),
  [3] = {.entry = {.count = 1, .reusable = true}}, SHIFT(14),
  [5] = {.entry = {.count = 1, .reusable = true}}, SHIFT(4),
  [7] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 2),
  [9] = {.entry = {.count = 1, .reusable = true}}, SHIFT(7),
  [11] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2),
  [13] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_return_expr_repeat1, 2), SHIFT_REPEAT(7),
  [16] = {.entry = {.count = 1, .reusable = true}}, SHIFT(11),
  [18] = {.entry = {.count = 2, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2), SHIFT_REPEAT(13),
  [21] = {.entry = {.count = 1, .reusable = true}}, REDUCE(aux_sym_for_expr_repeat1, 2),
  [23] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_expr, 3),
  [25] = {.entry = {.count = 1, .reusable = true}}, SHIFT(13),
  [27] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 3),
  [29] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_expr, 2),
  [31] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_table_expr, 1),
  [33] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_return_item, 1),
  [35] = {.entry = {.count = 1, .reusable = true}}, SHIFT(10),
  [37] = {.entry = {.count = 1, .reusable = true}}, SHIFT(20),
  [39] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_for_item, 3),
  [41] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 2),
  [43] = {.entry = {.count = 1, .reusable = true}}, SHIFT(12),
  [45] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_flowr_expr, 1),
  [47] = {.entry = {.count = 1, .reusable = true}}, REDUCE(sym_source_file, 1),
  [49] = {.entry = {.count = 1, .reusable = true}},  ACCEPT_INPUT(),
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
