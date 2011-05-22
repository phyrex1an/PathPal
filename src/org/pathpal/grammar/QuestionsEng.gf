concrete QuestionsEng of Questions = {
  lincat
    Question = {s : Str} ;
  lin
    AskGoTo d = {s = "Do you want to go to" ++ d.s ++ "?"} ;
    DString1 = {s = "dummy"} ;
    DString2 = {s = "dummy or dummy "} ;
    DString3 = {s = "dummy, dummy or dummy"} ;
    WalkOrCar = {s = "Do you want to walk or go by car ?"} ;
    WalkOrCarTo d = {s = "Do you want to walk or go by car to" ++ d.s ++ "?"} ;
    UnrecognizedWaypoint d  = {s = "Didnt recognize " ++ d.s ++ ", where do you want to go?"} ; 
}