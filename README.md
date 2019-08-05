# dcc-ascom

Uses [DigiCamControl](https://github.com/dukus/digiCamControl)'s amazing abilities at controlling nikon cameras for implementing a REST based ASCOM camera driver. See [Ascom Alpaca](https://ascom-standards.org/api/) and [Ascom Remote](https://github.com/ASCOMInitiative/ASCOMRemote). Good stuff!

# To use
For now I dont have binaries, but once I do the realease page will have precompiled jar files you could run anywhere. Once https://github.com/dukus/digiCamControl/issues/333 is done you could simply configure the rest endpoint of DCC's web server. Until then this will have to run on a windows machine where it could run `CameraControlRemote.exe` directly.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2019 FIXME
