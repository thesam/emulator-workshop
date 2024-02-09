# emulator-workshop
An emulator lets you run code written for another machine on your machine.
## Examples
* Run old games
* Run old applications
* Develop mobile apps
## How do emulators work?
By implementering the behaviour of the original hardware with new software.
* CPU
* Memory
* Graphics
* Sound
* ...
### Emulator main loop:
The main loop of the emulator acts as the CPU of the emulated hardware.
* Fetch
* Decode
* Execute
## What are we doing today?
We are going to write an emulator for the CHIP-8 virtual machine.
## Why CHIP-8?
* Few instructions/opcodes (only 35)
* Popular among people getting started with emulators (easy to Google)
* Virtual machine, not hardware (technically NOT an emulator)
## How?
* By following this guide: https://tobiasvl.github.io/blog/write-a-chip-8-emulator/
* Use any language you want
* A "state of the art" Java Swing application is included in this repo
## Resources
* Test suite: https://github.com/Timendus/chip8-test-suite
* JavaScript implementation: https://taniarascia.github.io/chip8/
