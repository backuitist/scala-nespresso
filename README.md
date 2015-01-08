Scala Presentation at Nespresso
===============================

This presentation is intended for Java developers with a bit of Scala exposure. 
We assume that you're already familiar with the following scala concepts:

 - trait
 - implicits (parameter & conversion)
 - companion object
 - case classes
 - higher-order functions

_Note: For those who need to get up to speed, I've added some links below_

The aim is to provide concrete real-world uses of recent scala features.
We'll cover the following points:

 - build tools : SBT hell or maven verbosity
 - string-interpolation
 - open-close principle with implicit value classes
 - type-classes
 - testing with matchete
 - macros
   - ansi-interpolator
   - mockito
   - dependency-injection with macwire
 - scala roadmap

Every topic is covered by a small playground project. The idea is to get you started during the presentation so that you can
experiment further later on, on your own.

## Prerequisites links

 * Scala for Java refugees (excellent introduction by Daniel Spiewak) : http://www.codecommit.com/blog/scala/roundup-scala-for-java-refugees
 * Scala documentation : http://docs.scala-lang.org/tutorials/tour/tour-of-scala.html
 * Implicits (parameter & conversion) : 
   * extensive doc : http://www.artima.com/pins1ed/implicit-conversions-and-parameters.html
   * stackoverflow recap : http://stackoverflow.com/questions/10375633/understanding-implicit-in-scala
 * Trait mechanic (aka _Class Linearization_) : http://jim-mcbeath.blogspot.ch/2009/08/scala-class-linearization.html