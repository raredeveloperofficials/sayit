Say It

A programming language that does teach you types, but doesn't force too much.

Say It is a programming language designed to make coding easier to write and understand, combining Python-like simplicity with Java/C++-inspired programming concepts.

---

⚠️ Project Development Notice

The original Say It compiler and runner are now deprecated.

The existing Java-based compiler and runner remain available and usable, but are no longer actively developed by the original developer.

Development is now planned around three components:

- Advanced Compiler — A redesigned compiler with a cleaner architecture and improved compilation system.
- Advanced Runner — A completely redesigned runtime designed to avoid the complexity and limitations of the legacy implementation.
- C++ Conversion Backend — A converter that translates Say It source files (".si") into C++ source (".cpp") and header (".hpp") files.

All three are planned as part of Say It's next development phase.

🎯 Shifting Purpose

Native Speed. Higher Syntax Power. Cleaner Architecture.

Say It is evolving beyond its original Java-based implementation.

The next development phase aims to provide two ways to process Say It programs:

1. Advanced Compiler and Runner

A completely redesigned compilation and execution system.

- Clean compiler architecture.
- Simplified bytecode representation.
- Redesigned runtime without carrying forward unnecessary complexity from the legacy implementation.
- Expanded support for functions, classes, inheritance, and other programming constructs.

2. C++ Conversion Backend

A separate compilation approach that converts Say It source files into C++ source and header files.

- Native Speed — Generated C++ can be compiled into native machine code.
- Higher Syntax Power — Expand Say It's expressive capabilities through classes, inheritance, and advanced programming constructs.
- Optimized Code Generation — Generate C++ code that can benefit from established C++ compiler optimizations.

The goal is to combine Say It's approachable syntax with both a redesigned native execution architecture and the optimization capabilities of established C++ toolchains.

---

🛠️ Built With

- Java — Original language implementation.
- Legacy Compiler and Runner — Existing Java-based bytecode system; deprecated but still available.
- Advanced Compiler — New compiler architecture planned.
- Advanced Runner — New runtime architecture planned.
- C++ — Target language for the conversion backend.
- "sic" Converter — Tool under development for converting ".si" files into ".cpp" and ".hpp" files.

---

📦 Getting Started

Say It is evolving from its original Java-based implementation toward a redesigned compiler, runner, and C++ conversion backend.

Legacy Implementation

The existing Java implementation remains available for users who want to run Say It through the original compiler and runner.

Advanced Compiler and Runner

The new compiler and runner are planned as redesigned replacements for the legacy architecture. Their implementation and supported features are under development.

New C++ Conversion Backend

The new backend is intended to convert Say It source files into C++ files.

Planned workflow:

sic path src "src"
sic path header "include"
sic convert main.si

The generated C++ files will then be compiled using a suitable C++ toolchain.

The advanced compiler, advanced runner, and C++ converter are all part of the planned development direction. Their architectures and supported features may evolve during development.

---

The existing Features, Examples, Philosophy, Contributing, License, Author, and First Update sections can remain as they are.