# Typed annotation guide

`@Typed` allows you to manually specify the type of a column in a `DatasetCatalog`.

Using `@Typed` is **optional**. If no type is specified, JQuery automatically determines the column type from the database.

---

What You Will Learn

In this guide, you will learn how to:

- Manually set a column type
- Let JQuery automatically detect column types

---

1. Manually Set a Column Type

To manually specify a column's type, add the `@Typed` annotation to the column.

<b>Basic Syntax</b>

```java
@Typed(JDBCType.myType)
ViewColumn myColumn();
```

The type must be one of the available `JDBCType` values.

<b>Example</b>

```java
@Bind("CUSTOMER_ID")
@Typed(JDBCType.UUID)
ViewColumn id();
```

In this example, the `id()` column is explicitly defined as a `UUID`.

| JQuery Column | Type |
|---------------|------|
| **id()** | `UUID` |

---

2. Automatic Type Detection

Using `@Typed` is **optional**.

If a column does not have a `@Typed` annotation, JQuery automatically determines its type from the corresponding database column.

<b>Example</b>

```java
@Bind("CUSTOMER_ID")
@Typed(JDBCType.UUID)
ViewColumn id();

@Bind("ADDRESS")
ViewColumn address();
```

In this example:

- `id()` is explicitly defined as `UUID`.
- `address()` has no `@Typed` annotation, so JQuery detects its type from the database.

If `ADDRESS` is declared as a `VARCHAR` in the database, JQuery will automatically use `VARCHAR` for `address()`.

| JQuery Column | Type |
|---------------|------|
| **id()** | `UUID` |
| **address()** | `VARCHAR` |

---

3. Supported Types

`@Typed` supports several types that map to standard SQL/JDBC types.

The following table shows each JQuery type, its SQL/JDBC equivalent, and the corresponding Java type.

| JQuery Type | SQL / JDBC Type | Java Type |
|-------------|-----------------|-----------|
| `BOOLEAN` | `BOOLEAN` | `Boolean` |
| `BIT` | `BIT` | `Boolean` |
| `TINYINT` | `TINYINT` | `Byte` |
| `SMALLINT` | `SMALLINT` | `Short` |
| `INTEGER` | `INTEGER` | `Integer` |
| `BIGINT` | `BIGINT` | `Long` |
| `REAL` | `REAL` | `Float` |
| `FLOAT` | `FLOAT` | `Double` |
| `DOUBLE` | `DOUBLE` | `Double` |
| `NUMERIC` | `NUMERIC` | `BigDecimal` |
| `DECIMAL` | `DECIMAL` | `BigDecimal` |
| `CHAR` | `CHAR` | `String` |
| `VARCHAR` | `VARCHAR` | `String` |
| `NVARCHAR` | `NVARCHAR` | `String` |
| `LONGNVARCHAR` | `LONGNVARCHAR` | `String` |
| `DATE` | `DATE` | `Date`, `java.util.Date`, `LocalDate` |
| `TIME` | `TIME` | `Time`, `LocalTime`, `OffsetTime` |
| `TIMESTAMP` | `TIMESTAMP` | `Timestamp`, `LocalDateTime`, `OffsetDateTime`, `ZonedDateTime`, `Instant` |
| `TIMESTAMP_WITH_TIMEZONE` | `TIMESTAMP_WITH_TIMEZONE` | `Timestamp`, `LocalDateTime`, `OffsetDateTime`, `ZonedDateTime`, `Instant` |
| `UUID` | `OTHER` | `UUID` |
| `JSON` | `OTHER` | `String` |
| `CLOB` | `CLOB` | `String` |
| `BLOB` | `BLOB` | `byte[]` |
| `BINARY` | `BINARY` | `byte[]` |
| `OTHER` | `OTHER` | `Object` |

<b>Example</b>

For example, if a column should be treated as an `INTEGER`, you can specify:

```java
@Bind("CUSTOMER_ID")
@Typed(JDBCType.INTEGER)
ViewColumn id();
```

<b>Summary</b>

`@Typed` is used to manually specify the type of a column.

When `@Typed` is not used, JQuery automatically determines the column type from the database.

- Use `@Typed` when you need to explicitly define a type.
- Omit `@Typed` when the database type should be used automatically.

---

✅ You can now control column types manually or let JQuery detect them automatically.