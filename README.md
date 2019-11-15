# Project (b1h2b)

To run with GUI, first install the Node dependencies:

```shell script
cd lib/gui
npm install
```

And then you can run the program with GUI with `ui.GUI.main()`. Port 27999 must be open for it to work.

To run just the GUI, use:

```shell script
cd lib/gui
electron .
```

To run without GUI, use `ui.Main.main()`. This will give you a simple REPL interface.