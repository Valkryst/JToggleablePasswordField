`JToggleablePasswordField` is a Java Swing component that extends the standard `JPasswordField` with the ability to toggle
between showing and hiding the password. It features a clickable visibility icon that allows users to temporarily reveal
their password to verify its correctness.

Requires Java 11.

## Table of Contents

* [Installation](https://github.com/Valkryst/JToggleablePasswordField#installation)
    * [Gradle](https://github.com/Valkryst/JToggleablePasswordField#-gradle)
    * [Maven](https://github.com/Valkryst/JToggleablePasswordField#-maven)
    * [sbt](https://github.com/Valkryst/JToggleablePasswordField#-scala-sbt)
* [Example](https://github.com/Valkryst/JToggleablePasswordField#example)

## Installation

JToggleablePasswordField is hosted on the [JitPack package repository](https://jitpack.io/#Valkryst/JToggleablePasswordField)
which supports Gradle, Maven, and sbt.

### ![Gradle](https://i.imgur.com/qtc6bXq.png?1) Gradle

Add JitPack to your `build.gradle` at the end of repositories.

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add JToggleablePasswordField as a dependency.

```
dependencies {
	implementation 'com.github.Valkryst:JToggleablePasswordField:2025.5.8'
}
```

### ![Maven](https://i.imgur.com/2TZzobp.png?1) Maven

Add JitPack as a repository.

``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add JToggleablePasswordField as a dependency.

```xml
<dependency>
    <groupId>com.github.Valkryst</groupId>
    <artifactId>JToggleablePasswordField</artifactId>
    <version>2025.5.8</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add JToggleablePasswordField as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "JToggleablePasswordField" % "2025.5.8"
```

## Features

* Adaptive styling which respects the current Look and Feel.
* Automatic scaling of the component's font and icon size based on the component's dimensions.
* Password visibility toggling with a clickable icon.

## Example

This creates a new `JToggleablePasswordField` and displays it in a `JFrame`.

```java
public class Driver {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JToggleablePasswordField passwordField = new JToggleablePasswordField("secret password");

            final JFrame frame = new JFrame("JToggleablePasswordField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(400, 100));

            final Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(passwordField, BorderLayout.CENTER);

            frame.setVisible(true);
            frame.pack();
            frame.setLocationRelativeTo(null);
        });
    }
}
```