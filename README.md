# Live Football World Cup Scoreboard

Implementation of a **Live Football World Cup Scoreboard** as an in-memory Java library.

This project was developed as part of a coding exercise, following a **Test-Driven Development (TDD)** approach and focusing on **clean design, simplicity, and correctness**.

---

## 📌 Features

The scoreboard supports the following operations:

- Start a new match with initial score `0 - 0`
- Update the score of an existing match
- Finish a match (remove it from the scoreboard)
- Get a summary of not finished matches ordered by:
  - **Total score (descending)**
  - **Most recently started match (for tied ordering)**

---

## 🧪 Approach

The implementation follows **TDD (Test-Driven Development)**:

1. Write a failing test
2. Implement the minimal solution
3. Refactor if needed

This results in:
- High test coverage
- Clear commit history
- Incremental design evolution

---

## ⚙️ How to Run

### 1. Run tests (recommended)

```bash
mvn test
```

---

### 2. Build the project

```bash
mvn clean install
```
---

## 🧠 Design Decisions

### 1. In-Memory Storage

The scoreboard uses an in-memory `List<Match>`.

**Why:**

* Keeps the implementation simple (as required)

---

### 2. Sorting in `getSummary()`

Sorting is applied only when retrieving the summary:

```java
matches.stream().sorted(...)
```

**Why:**

* Keeps internal storage simple and unsorted
* Avoids maintaining ordering on every insert/update
* Aligns with requirement: ordering is a *presentation concern*

**Trade-off:**

* Sorting happens on every call (`O(n log n)`)
* Acceptable given the scope (in-memory, small dataset)

---

### 3. Duplicate Detection using Streams

Duplicate matches are checked using:

```java
matches.stream().anyMatch(...)
```

**Why:**

* Simple and expressive
* Matches the requirement to keep the solution simple

**Trade-off:**

* Linear lookup (`O(n)`)
* Could be optimized with a `Map<MatchKey, Match>` which would simplify update and duplicate checks especially if scaling occured. The exercise explicitly asks for the simplest working solution. So a `List` was intentionally chosen here.

---

### 4. Tie-Breaking Strategy in Ordering

To handle matches with equal total scores:

* A long data type `startOrder` field is used
* Each new match gets a positive increasing value

This ensures deterministic ordering for ties. This was preferred instead of a timestamp because of:

* Deterministic behavior
* Easier to test
* No dependency on system clock

**Trade-off:**

* Does not represent real-world time (not required for this problem according to my assumption)

---

### 5. Prefix Increment (`++counter`)

```java
++nextStartOrder
```

**Why:**

* Ensures ordering starts from `1` for better semantics.

---

### 6. Input Normalization (Trimming)

Team names are normalized using `.trim()` before validation and storage.

**Why:**

* Prevents duplicates caused by whitespace differences
* Ensures consistent data representation
* Avoids subtle edge cases

---

## 📏 Assumptions

The following assumptions were made:

* Team names are matched **exactly after trimming**
* A match is uniquely identified by `(homeTeam, awayTeam)`
* Reverse fixtures are treated as **different matches**
* Score updates are **absolute values**, not incremental
* Only matches currently in progress can be:

  * updated
  * finished
* The scoreboard is:

  * **in-memory only**
  * **not thread-safe**

---

## 🧪 Testing Strategy

The solution was developed using **Test-Driven Development (TDD)**.

Each feature was implemented in small steps:

* Write failing test
* Implement minimal solution
* Refactor if needed

### Commit Strategy

The repository contains **many small, incremental commits**.

**This allows to:**

* Clearly demonstrating the thought process
* Show step-by-step evolution of the solution
* Reflect my real-world development workflow

---

## 🚀 Possible Improvements

If it was extended beyond the exercise's limits:

* Replace `List` with `Map<MatchKey, Match>` for faster lookups
* Introduce thread-safety (e.g., concurrent collections)
* Expose as REST API

---

## 💬 Summary

This implementation focuses on:

* Simplicity
* Testability
* Readability
* Correctness

---