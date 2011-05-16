abstract Locator = {
  flags startcat = Phrase ;
  cat
    Phrase;
    Dummy;
    StoreType;
    Answer;
    
  fun
    GoTo : Dummy -> Phrase ;
    DString : Dummy ;
    GoFromTo : Dummy -> Dummy -> Phrase;
    FindStore : StoreType -> Phrase ; 
    WalkOrTrans : Answer -> Phrase;
    Walking, Transportation: Answer;
}
