# Contributing to Project: Gimli

Project: Gimli is available on [Codeberg](https://Codeberg.org/Laxystem/Gimli), and mirrored on [GitHub](https://GitHub.com/Laxystem/Gimli).

## Always Ask, Not to Ask

Always ask if unsure. Don't ask to ask, just ask.

Creating an entire issue for minor problems and/or questions is fine and encouraged, just make sure your question hasn't been asked already.

An exception to this rule is "when". Gimli is a hobby project.

## PR Guide

Always PR into the Codeberg repo. Creating a Codeberg account is as easy as clicking "Register", "log in with", then your favorite forge.

### Merge/Rebase/Squash

We only allow merges, because rebasing removes commit signatures, and squashes destroy the branch graph.

### Branch to PR Into

Generally, always PR into the `development` branch, unless there's a special branch for the feature you're adding.

## Libraries, Frameworks & Tooling

Make sure you know how the libraries and frameworks Gimli uses work (or at least, how to use them), especially [Kotlin](https://kotlinlang.org/docs), [SQL](https://postgresql.org/docs), and [KotlinX Coroutines](https://kotlinlang.org/docs/coroutines-guide.html).

## Out of Scope

Feel free to fork Gimli if you want to add any of the below features, because we won't. Try convincing the maintainer otherwise on their [mastodon account](https://tech.lgbt/@laxla), not via issues nor PRs. 

If you don't know how to (theoretically) implement an out-of-scope feature, and/or won't be able to help, rest assured it'll remain out of scope.


### Turning Gimli Into a Forge

Gimli is a social network, not a git forge. *However*, adding compatibility between Gimli and ForgeFed features is within scope, and actually planned. Gimli *won't* host Git repositories.

### Cryptocurrency & NFTs

Sorry, the maintainer's cryptography knowledge is lacking. Besides, the real Web3 is the Fediverse.

### Custom Profile HTML/CSS/JS/etc.

Gimli is intended to be a cross-platform project. As such, we need to suppoort platforms that don't support HTML, CSS, and JS - which are all officialy supported platforms, except for the web client. Gimli tries to allow profiles to be customized in other ways.

### Other Languages

Gimli is built in Kotlin. Usage of other languages is only allowed when strictly necessary, to allow anyone to contribute to any part of this monorepository.

Gimli may have projects in other languages (in a separate repository) in the future.

## FAQ
### What's the `laxystem` branch?
It's the maintainer's personal branch, because Codeberg doesn't  allow to fork your own repos.


## Code Style

Note the below is merely guidelines. Feel free to ignore them in cases they produce cursed code.

* "Code element" and "element" are any `fun`ction, `val`ue, `var`iable, `class`, `interface`, `object`, etc.
* "Property" is any Kotlin property.
    * "`val`ue" is an explicitly immutable property.
    * "`var`iable" is an explicitly mutable proeprty.

### Nesting & Indentation

Gimli code focuses on readability, not on nesting.

Kotlin is a nesting-heavy language, and nesting purists will sometimes have to survive over *six* levels of nestings.

For indentation, use four spaces or a tab character.
However, do *not* refactor lines you haven't otherwise changed to use your favorite, to avoid uselessly long diffs.

Gimli may restrict indentation further in the future, only allowing tabs.

### Files
This repository contains two types of Kotlin files.
* [Utility Files](#utility-files)
* [Class Files](#class-files)

Sort code elements by usage.
If `foo()` calls `bar()`, `bar()` should be written first.


#### Licensing

The following should be at the top of every file:
```kotlin
/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) <latest update year> Project: Gimli and contributiors (<previous contributors>, <your name here>).
 */
```

When you change a file, add your name where `<your name here>` is (unless it's already there).

For example:

```kotlin
/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project: Gimli and contributors (Laxystem).
 */
```

#### Utility Files

Contain *zero* non-`private` top-level `class`es, `interface`s and `object`s.

Have a variety of utility `fun`ctions and `val`ues.

May be turned into Class Files by wrapping them with an `object`, or with a `class` and `companion object`.

Prefer only using Utility Files in [`:util`](util/README.md) and its submodules, where the classes are defined in other modules.
When creating extension functions for a `class` defined in the same module, define them in its or a related `companion object`.

#### Class Files

Contain a *single* non-`private` top-level code element, which must be `class`, `interface`, or `object`.

Use a `companion object` to declare extension functions within the same file.

Within classes, sort elements in the following way. Note that within categories,
elements must be sorted the same way as top-level elements.

```kotlin
private class MyClassImpl() // used by MyClass

class MyClass {
    // field-ful values
    val value = TODO()
    // field-ful variables
    var variable = TODO()

    // <overriding field-ful values and variables>
    
    // fieldless values
    val getter get() = TODO()
    // fieldless variables
    var getterAndSetter
        get() = TODO()
        set() = TODO() 

    // <overriding fieldless values and variables>
    
    // secondary constructors
    constructor()

    // functions and sub-classes
    fun function() = TODO()

    class subclass()

    override fun overridingFunction() = TODO()

    // and lastly, the companion object

    companion object
}
```