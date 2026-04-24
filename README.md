# SurrealDB Java SDK — JVM crash when accessing array-based RecordId

## The Bug

When a record is created with an **array-based `RecordId`** (e.g. `myDoc:["myTenant", rand::ulid()]`),
calling `.getArray()` on the `Value` returned by `RecordId.getId()` causes a **JVM crash (SIGSEGV)**:

```
SIGSEGV (0xb) at Klass::method_at_vtable(int)
```

The crash happens inside the native layer of the SDK (`libjvm.so`), not in user code.  
A full crash log is included in [`hs_err_pid47358.log`](hs_err_pid47358.log).

## Environment tested

| | |
|---|---|
| **OS** | Ubuntu 24.04.4 LTS (Linux x86-64) |
| **JDK** | OpenJDK Runtime Environment Temurin-25+36 (build 25+36-LTS) |
| **JDK** | OpenJDK Runtime Environment Microsoft-13053558 (25.0.2+10) (build 25.0.2+10-LTS) |
| **SurrealDB Java SDK** | 2.0.0 |
|---|---|
| **OS** | Microsoft Windows 11 |
| **JDK** | OpenJDK Runtime Environment Temurin-25.0.2+10 (25.0.2+10) (build 25.0.2+10-LTS) |
| **SurrealDB Java SDK** | 2.0.0 |

## How to run

### Prerequisites

- Java 25+ on your `PATH`
- The `surrealdb-2.0.0.jar` file (can be found in your local mvn repository)

### Run directly with `java` (single-file source launcher)

```bash
java -cp surrealdb-2.0.0.jar Main.java
```

The JVM will crash with a `SIGSEGV` and write an `hs_err_pidXXXX.log` file.


## Other bugs and improvements proposals
I'm using this repo to track some improvements and bugs on the Surreal Java SDK.

- [ArrayBasedRecordIds.java](ArrayBasedRecordIds.java): I'd like to be able to use Array based RecordIds.
- [NullableBoolean.java](NullableBoolean.java): nullable Boolean are not handled properly i've found a workaround that sucks but works.
- [UseRecordInsteadOfClass.java](UseRecordInsteadOfClass.java): Java records is a good way to represent an immutable object i'd love Surreal to handle them.
- [UseSurrealPojoWithoutSurrealInit.java](UseSurrealPojoWithoutSurrealInit.java): Instantiating a Surreal POJO (such as RecordId) should not rely on the rust code.

You can run these files just as the example above:
```bash
java -cp surrealdb-2.0.0.jar NullableBoolean.java
```
