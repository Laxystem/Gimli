# Project: Gimli: Cintribution Guide

Project: Gimli is available on [Codeberg](https://Codeberg.org/Laxystem/Gimli), and mirrored on [GitHub](https://GitHub.com/Laxystem/Gimli).

## PR Guide

Always PR into the Codeberg repo. Creating a Codeberg account is as easy as clicking "Register", "log in with", then your favorite forge.

### Merge/Rebase/Squash

We only allow merges, because rebasing removes commit signatures, and squashes destroy the branch graph.

### Branch to PR into

Generally, always PR into the `development` branch, unless there's a special branch for the feature you're trying to add. Create an issue for the feature you're trying to add, and ask if unsure.

## Libraries

Make sure you know how the libraries and frameworks Gimli uses work (or at least, how to use them), especially [Kotlin](https://kotlinlang.org/docs), [SQL](https://postgresql.org/docs), and [KotlinX Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)

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
It's the maintainer's personal branch, because Codeberg doesn't  allow to fork your own repos. Don't PR into it.

