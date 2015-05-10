GameEngine
==========

Simple game engine for 2D games in swing and awt.
Supports running in fullscreen exclusive mode.

All drawing is organized with stages, which provides the basic drawing and buffering.
The individual part is done with the Scene and SceneObject classes.

Basic collision detection (bounding box, alpha collision) is implemented by default.

A timer class (Clock) handles the repaint job, so that you only have to provide the time based paint methods in your SceneObjects.

Several utility classes for loading and displaying images can be used. Sprites and 9patch images are supported.

A basic loading screen and SceneObjects for text drawing(LabelObject), animated text drawing(CachedLabelObject)
and sprite rendering(SpriteSceneObject, Sprite2DSceneObject) are ready to use.

Sample programs using this library can be found at: https://github.com/brunothg/JellySplush or https://github.com/MuesLee/n8dA