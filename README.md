# <h1 style="text-align:center;">Design a Chess Game</h1>
<p style="text-align:center;">This repository contains a chess game with proper system design journey using Java.</p>
<p style="text-align:center;">
  <img src="https://raw.githubusercontent.com/thisismemukul/chess/refs/heads/main/demo.png" alt="Chess Game with Java" title="Games with java show png" width="500"/>
</p>

<p> The main classes will be: </p>
<ul>
<li><b>Spot: </b> A spot represents one block of the 8×8 grid and an optional pieces.</li>
<li><b>Piece: </b> The basic building block of the system, every pieces will be placed on a spot. Piece class is an abstract class. The extended classes (Pawn, King, Queen, Rook, Knight, Bishop) implements the abstracted operations.</li>
<li><b>Board: </b> Board is an 8×8 set of boxes containing all active chess pieces.</li>
<li><b>Player: </b> Player class represents one of the participants playing the game.</li>
<li><b>TODO Move: </b> Represents a game move, containing the starting and ending spot. The Move class will also keep track of the player who made the move.</li>
<li><b>Game: </b> Player class represents one of the participants playing the game.</li>
</ul>



### Project Folder Structure
```css
/src
 ├── enums
 │   ├── PieceColor.java
 │   └── PieceType.java
 ├── pieces
 │   ├── PieceColor.java
 │   └── PieceType.java

 │   │   └── com
 │   │       └── agooddeveloper
 │   │           └── spring
 │   │               └── ai
 │   │                   └── assistant
 │   │                       ├── config
 │   │                       │   └── WebConfig.java
 │   │                       ├── constants
 │   │                       │   ├── Constants.java
 │   │                       │   └── RestURIConstants.java
 │   │                       ├── controller
 │   │                       │   ├── AIController.java
 │   │                       │   └── AIImageGenController.java
 │   │                       ├── dto
 │   │                       │   ├── ImageResponseDto.java
 │   │                       │   ├── IngredientImageDto.java
 │   │                       │   ├── InstructionImageDto.java
 │   │                       │   └── TitleImageDto.java
 │   │                       ├── enums
 │   │                       │   └── ResponseCode.java
 │   │                       ├── exceptions
 │   │                       │   ├── IBaseError.java
 │   │                       │   ├── DefaultBaseError.java
 │   │                       │   ├── GlobalExceptionHandler.java
 │   │                       │   └── ValidationException.java
 │   │                       ├── helper
 │   │                       │   ├── ImageGenerationHelper.java
 │   │                       │   └── AiHelper.java
 │   │                       ├── response
 │   │                       │   ├── ApiResponse.java
 │   │                       │   └── diet
 │   │                       │       ├── DayMealPlan.java
 │   │                       │       ├── DietPlanResponse.java
 │   │                       │       ├── Meal.java
 │   │                       │       ├── MealSuggestion.java
 │   │                       │       └── NutritionalDietInformation.java
 │   │                       │   └── trainer
 │   │                       │       ├── exercise
 │   │                       │       │   ├── DailyWorkoutPlan.java
 │   │                       │       │   ├── Exercise.java
 │   │                       │       │   └── ExercisePlanResponse.java
 │   │                       │       └── recipe
 │   │                       │           ├── NutritionalInformation.java
 │   │                       │           └── RecipeResponse.java
 │   │                       ├── service
 │   │                       │   ├── chatservice
 │   │                       │   │   ├── IAIService.java
 │   │                       │   │   └── impl
 │   │                       │   │       └── ChatService.java
 │   │                       │   ├── imageservice
 │   │                       │   │   ├── IImageService.java
 │   │                       │   │   └── impl
 │   │                       │   │       ├── OpenAIImageService.java
 │   │                       │   │       └── StabilityAIImageService.java
 │   │                       │   └── trainerservice
 │   │                       │       ├── ITrainerService.java
 │   │                       │       └── impl
 │   │                       │           ├── RecipeService.java
 │   │                       │           ├── PersonalGymTrainerService.java
 │   │                       │           └── DietPlannerService.java
 │   │                       ├── utils
 │   │                       │   ├── ImageGenUtil.java
 │   │                       │   ├── LoggerUtil.java
 │   │                       │   ├── ObjectMapperUtil.java
 │   │                       │   └── TrainerServiceUtil.java
 │   │                       └── SpringAiAssistantApplication.java
 │   └── resources
 │       └── application.properties
 └── test
     └── java
         └── com
             └── agooddeveloper
                 └── spring
                     └── ai
                         └── assistant
```

```scss

fromX: 1
toX: 3
direction: 1
startRow: 1

(x,y)
+----------------------------------------------> Y
0  | (0,0)   (0,1)   (0,2)   (0,3)   (0,4)   (0,5)   (0,6)   (0,7)
1  | (1,0)   (1,1)   (1,2)   (1,3)   (1,4)   (1,5)   (1,6)   (1,7)
2  | (2,0)   (2,1)   (2,2)   (2,3)   (2,4)   (2,5)   (2,6)   (2,7)
3  | (3,0)   (3,1)   (3,2)   (3,3)   (3,4)   (3,5)   (3,6)   (3,7)
4  | (4,0)   (4,1)   (4,2)   (4,3)   (4,4)   (4,5)   (4,6)   (4,7)
5  | (5,0)   (5,1)   (5,2)   (5,3)   (5,4)   (5,5)   (5,6)   (5,7)
6  | (6,0)   (6,1)   (6,2)   (6,3)   (6,4)   (6,5)   (6,6)   (6,7)
7  | (7,0)   (7,1)   (7,2)   (7,3)   (7,4)   (7,5)   (7,6)   (7,7)
|
↓
X


a   b   c   d   e   f   g   h
+---+---+---+---+---+---+---+---+
8 | B | W | B | W | B | W | B | W |
+---+---+---+---+---+---+---+---+
7 | W | B | W | B | W | B | W | B |
+---+---+---+---+---+---+---+---+
6 | B | W | B | W | B | W | B | W |
+---+---+---+---+---+---+---+---+
5 | W | B | W | B | W | B | W | B |
+---+---+---+---+---+---+---+---+
4 | B | W | B | W | B | W | B | W |
+---+---+---+---+---+---+---+---+
3 | W | B | W | B | W | B | W | B |
+---+---+---+---+---+---+---+---+
2 | B | W | B | W | B | W | B | W |
+---+---+---+---+---+---+---+---+
1 | W | B | W | B | W | B | W | B |
+---+---+---+---+---+---+---+---+

```
### Order of Implementation
1. **Define the ENUMS for Piece Color and Type**
    1. PieceColor.java
    2. PieceType.java
2. **Define the pieces for each Piece**
    1. Piece.java
    2.
3. 3
4. 4
5. 5
6. 6
7. 7
8. 8
9. 9
10. 0