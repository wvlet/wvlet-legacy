wvlet [![GitHub Actions][gha-badge]][gha-link]
==========================================

[gha-badge]: https://github.com/wvlet/wvlet/workflows/CI/badge.svg
[gha-link]: https://github.com/wvlet/wvlet/actions?workflow=CI

![wvlet](logos/wvlet-banner-white-tiny.png)

The wvlet, pronounced as _weave-let_, is a new query language for all types of data, including SQL engines (DBMSs), local files, programing language objects, etc.

wvlet queries (.wv) are designed to be:
- Composable. wvlet queries can be defined as functions, which can be reused for other data processing.
- Reproducible. You can build a reproducible data procesing pipeline with time-window based incremental processing.

wvlet queries will be compiled into SQL queries and code for target programming languages.
