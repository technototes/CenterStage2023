# Notes for Kevin

Hi, there. I'm the lead mentor for 16750/20403, and I'm a professional software
developer. There are a couple scripts in here, of various levels of value.

## Scripts

There are some scripts you can invoke from the package.json file that are useful
for doing some stuff:

### `flip.js`

This script comments/uncomments lines of text according to the `fileList` map.
As of this writing, there are two uses of this script: One enables/disabled
using [TechnoLib](https://github.com/technototes/TechnoLib) as a local library,
instead of using the published one. This is helpful when you're trying to debug
something that fails inside the library, as well as when you're fixing/changing
the library. You just need to clone the repo at the root of this repo and make
sure it's named `TechnoLib` and you're good to go.

### `fastforward.js`

I used this script last year to move all branches forward. I'm not using it
anymore, as I'm trying a different workflow from last year. It does have some
usages of the `git` NPM package so keeping it around as an example isn't
terrible.

## Workflow

There's a "workflow challenge" with students that I'm trying to solve. We share
our code on github. The students are mostly fine, but a proper workflow requires
that the students perform 3 or 4 steps when they open up a laptop to start the
day, and perform a couple when they're done. Honestly it's probably more
discipline than I could pull off, particularly if I didn't have a very clear
understanding of the cost of getting it wrong.

I'm considering automating this, as they're easy to forget, do in the wrong
order, etc... But first, let's walk through what the steps are:

1. Grab a laptop (with a repository already cloned)
2. Check out main
3. Pull from main
4. Create a new branch with the students name, the date, and the 'intentions' of
   the branch
5. CODE!!!
6. TEST!!!
7. Commit code to local branch
8. Push local branch to github
9. If it was tested and works (and/or does no harm) put up a Pull Request

Rinse and repeat

There are a few places along the way where students can make mistakes, even with
the best of intentions. I want to create a tool that provides all the things
that students may want/need to do with good checks along the way.

### Problems to help prevent from occurring

- Ensure that all github 'stuff' occurs while connected to the interwebs (and
  not a control hub)
  - This is probably addressable by testing the ip address
- Make sure that there are no 'dirty' files in the repo
- Auto-format the code (ideally, as a _separate_ commit, just in case)
- Local build testing
- Removing ancient branches

### Other capabilities worth adding

- Connection/disconnection from a robot (REV hardware client is unreliable, and
  only available for Windows)
  - I _might_ even be able to switch Wi-Fi networks automatically. I haven't
    looked at what you can do from a Node script (requirement: Fully
    cross-platform...)
- Launching the FTC Dashboard
- Maybe detection of "Yeah, powercycle the control hub"?
- Launching Android Studio to begin a coding session?
- Configuration stuff:
  - Setup a .gitconfig file with all my silly aliases
  - Anything else worth doing?
- If it's a 'stand-alone' app, here are some things that would be cool
  - Cloning the repo?
  - _Finding_ the repo
  - Installing git, nodejs, Android Studio, etc...

Since I'm not really excited about the idea of this whole thing being packaged
as a gigantic electron app, but I _do_ want some amount of UI, though, I might
try to just make a little WebView app, maybe using Neutralino (though I kinda
hate that project for making their own custom build system...) or maybe just
have a script that launches a local web server and the whole thing interacts
through that? Or maybe just use Electron and be done with it :/

But in the short term, I'm going to just make it a yarn script (Maybe
`yarn work`?)

### Design ideas

Remember: Kevin is #NotADesigner

- Upon launch, you have 3 choices:
  - New Setup
  - Begin some work
  - Call it a day
