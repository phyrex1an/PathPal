abstract Questions = {
  flags startcat = Question ;
  cat
    Question; DummyString; TravelMethod ;

  fun
    AskGoTo : DummyString -> Question ;
    DString1 : DummyString ;
    DString2 : DummyString ;
    DString3 : DummyString ;
    WalkOrCar : Question ;
    WalkOrCarTo : DummyString -> Question ;
    UnrecognizedWaypoint : DummyString -> Question ;
    WhereToGo : Question ; 
    WhereToGoBy : TravelMethod -> Question ;
    Car : TravelMethod ;
    Walk : TravelMethod ;
}