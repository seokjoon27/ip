# AI.md — AI-assisted coding log

> This project intentionally used AI tools (ChatGPT) to boost productivity.  
> This file records where/how AI was used, as required by the iP.AI route and A-AiAssisted.

## Tools
- ChatGPT (reasoning assistant) — prompts and code review

## Where AI helped (highlights)
- CI stabilization across Ubuntu/macOS/Windows (runtest.sh / runtest.bat, CRLF/exec bits, JavaFX headless detect)
- Fixing Checkstyle (javadoc, coding standards check)
- Text UI tests: diagnosing failures and normalizing expected output spacing (debugging .xml files)
- Git hygiene: Examples of proper conventional full commit messages
- idea generation (Best choice for BCD-Extensions according to my code functions, ways to use OOP wisely, etc.)

## Notes/Observations
- Prompting with *“show exact diff and full commands”* worked best.
- Headless compile on CI avoids JavaFX runtime on Windows/macOS runners.
- Keeping tiny, descriptive commits made failures easier to bisect.
- Generation of proper formatting of coding standards, full commits, and md/xml/sh files helped me learn faster.

## Credits in code
Some methods include inline comments acknowledging AI assistance (see `Parser` and `checkstyle.xml`).