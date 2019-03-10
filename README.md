# The Shadow Over Darkmoor

A Clojure library designed to ... well, that part is up to you.

## Build Tool

This application uses [Leiningen](https://leiningen.org/) to handle the builds/dependencies.

## Code

The code is located in 'src/darkmoor/core.clj', and requires text documents found in
the 'resources' file.

Source code can be run without compilation by running:
```$ lein run```
anywhere in Darkmoor's file system. This won't be as fast as running it off of a 
standalone jar.

To build a standalone jar:

    ```$ lein compile; lein uberjar```

Afterwhich that can be run with:

    ```$ java -jar target/uberjar/darkmoor-0.1.0-SNAPSHOT-standalone.jar```


## Acknowledgements

Thanks to my wife, without whom I never would have been able to complete this.

All ASCII art taken from: 

patorjk.com/software/taag

www.asciiart.eu/

ascii.co.uk/

## License

Copyright © 2019 CM Rutz 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
