Chapter 11: Advanced Touch Input
================================

 

### Group 3

-   Tran Quang Duy - <duy9968@gmail.com>

-   Vu Binh Duong - <badboy.hp96@gmail.com>

-   Pham Tien Thanh - <vicnoob.gg@gmail.com>

 

### Moving / Resizing a view

Move and resize view at runtime, based on user’s interaction

Use \`\`\`\`LayoutParams\`\`\`\`

1.  Get view's LayoutParams from the type of its parent

2.  Modify position, size

3.  Set back to the view

 

### Simple single-touch gestures

Single touch gestures: hold, drag, fling

Two main ways to handle:

-   Manual analyzer with onTouch:

    -   Use on touch listener

    -   Override onTouch

    -   Analyze touch information: get type (finger touch, move, lift) and
        position

-   Simplified with GestureDetector: a class for detecting common gestures

    -   Create the class

    -   Override desired methods: onDown(), onFling(), onDoubleTap(),….

    -   Apply in view’s setOnTouchListener
