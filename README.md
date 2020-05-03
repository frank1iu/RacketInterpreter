# Racket Interpreter

### A simple interpreter for Racket written in Java.

To run with GUI, first install the Node dependencies:

```shell script
cd lib/gui
npm install
```

And then you can run the program with GUI with `ui.GUI.main()`. The interpreter will listen on port 27999 - make sure it is not being forwarded to the Internet.

To run without GUI, use `ui.Main.main()`. This will give you a simple REPL interface.