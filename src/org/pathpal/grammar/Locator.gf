abstract Locator = {
  flags startcat = Phrase ;
  cat
    Phrase;
    Adress;
    AdressTree;
    StoreType;
    Answer;
  fun
    GoTo : AdressTree -> Phrase ;
    AdressT : String -> AdressTree -> AdressTree;
    AdressL : String -> AdressTree;
    GoFromTo : AdressTree -> AdressTree -> Phrase;
    FindStore : StoreType -> Phrase ; 
    WalkOrTrans : Answer -> Phrase;
    Walking, Transportation: Answer;
}
