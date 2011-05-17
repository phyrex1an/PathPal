abstract Locator = {
  flags startcat = Phrase ;
  cat
    Phrase;
    Dummy;
    StoreType;
    Answer;
    TransItem;
    WalkItem;
  fun
    GoTo : Dummy -> Phrase ;
    GoByCarTo : Dummy -> Phrase ;
    GoByCarFromTo : Dummy -> Dummy -> Phrase ;
    GoToVia : Dummy -> Dummy -> Phrase; 
    GoFromToVia : Dummy -> Dummy -> Dummy -> Phrase;
    WalkTo : Dummy -> Phrase;
    DString : Dummy ;
    GoFromTo : Dummy -> Dummy -> Phrase;
    FindStore : StoreType -> Phrase ; 
    WalkOrTrans : Answer -> Phrase;
    Walking : WalkItem -> Answer;
    Transportation : TransItem -> Answer;
    Walk,Walk2,Foot : WalkItem;
    Car,Vehicle : TransItem;
    TBy: TransItem -> TransItem;
}
