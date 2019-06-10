# motli
A command-line tool for generating Android-themes based on color palette and xml templates.

## How to run
You only need to provide path to your config and *motli* will take care of the rest. Something like this:

```sh
java -jar motli.jar /Users/test/my-config.json
```

## Config
Config is a simple JSON file that should contains the following information:
* *dataDirectory* - path to the directory where all your resource templates (like drawables and color-state-lists) 
are located;
* *paletteFile* - path to the file with color palette;
* *outputDirectory* - path to the directory where to put generated files;
* *themes* - array of theme names to be generated;
* *parentTheme* - parent theme for all generated themes.

Config example:
```json
{
    "dataDirectory": ".",
    "paletteFile": "palette.json",
    "outputDirectory": "../generated-motli",
    "themes": ["green", "dark", "blue"],
    "parentTheme": "@style/AbstractTheme"
}
```

## How to make theme resources
In *dataDirectory* described above you can put one of the following sub-directories:
* *color* - for templates of color-state-list resources;
* *drawable* - for templates of drawable resources.

### What is resource template?
Resource template is a regular XML file. Just like usual resource file for an Android app. But instead of using 
concrete color or another theme resource you need to use placeholder. Something like this:
* `${COLOR.text_primary}` for colors (where *text_primary* is the name of the color from your palette);
* `${DRAWABLE.button_primary_pressed}` for references to another theme resource (where *button_primary_pressed* name 
of the file with resource).

**Motli** will produce as many files as many themes you set up in the config described above (look at *themes* array). 
Placeholder will be resolved to the corresponding color or resource of each theme.

For example for the following drawable shape
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:dither="true"
    android:shape="rectangle">
    <corners android:radius="4dp" />
    <solid android:color="${COLOR.primary}" />
</shape>
```
**motli** will generate 3 files: for green, dark and blue theme respectively (look at config example). And the placeholder
will be replaced with *primary* color from palette of each theme. For instance for green theme it will looks like:
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:dither="true"
    android:shape="rectangle">
    <corners android:radius="4dp" />
    <solid android:color="@color/primary_green" />
</shape>
```
And so on.

### Resource qualifiers
You are able to add resource qualifiers directly to the template filename. **Motli** will generate resource for each 
theme and put them in the corresponding resource-folder with same qualifiers.

For example:
```
ripple_with_background-v21.xml
```

**Be careful** that **Motli** do not verify qualifiers and its order.

## Palette
Palette is a simple JSON file with named colors for each theme:
```json
{
  "version": 1,
  "themes": {
    "theme1": {
      "ghost_inverse": "#ffa164f7",
      "primary": "#ff23c7de",
      "secondary_base_pastel": "#ffff4797"
    },
    "theme2": {
      "ghost_inverse": "#ff40d295",
      "primary": "#fffe9a3f",
      "secondary_base_pastel": "#ff2e91ff"
    }
  }
}
```

* *version* - version of palette format;
* *themes* - map where keys are theme names and values are theme colors

**Pay attention** to that each theme must contains the same set of color names. From above example
each of two themes has 3 colors. And the names of the colors are the same for each theme.

You may be interested in [our tool](https://github.com/mail-ru-im/motli-sketch-palette) that converts 
specially formed *.sketch* palettes in **motli** JSON format.

## License

Copyright (c) 2019 Mail.Ru IM

Licensed under the [MIT](LICENSE) License. 