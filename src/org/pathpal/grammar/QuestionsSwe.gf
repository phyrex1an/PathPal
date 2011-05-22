concrete QuestionsSwe of Questions = {
  lincat
    Question = {s : Str} ;
  lin
    AskGoTo d = {s = "Vill du åka till" ++ d.s ++ "?"} ;
    DString1 = {s = "dummy"} ;
    DString2 = {s = "dummy or dummy "} ;
    DString3 = {s = "dummy, dummy or dummy"} ;
    WalkOrCar = {s = "Vill du gå eller åka bil ?"} ;
    WalkOrCarTo d = {s = "Vill du gå eller åka bil till" ++ d.s ++ "?"} ;
    UnrecognizedWaypoint d  = {s = "Känner inte igen " ++ d.s ++ ", vart vill du åka?"} ; 
    WhereToGo = {s = "Vart vill du åka?" } ; 
    WhereToGoBy d = {s = "Vart vill du " ++ d.s ++ "?"} ; 
    Car = {s = "köra"} ;
    Walk = {s = "gå"} ;
}