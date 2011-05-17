abstract Locator = {
  flags startcat = Phrase ;
  cat
    Phrase;
    Dummy;
    StoreType;
    Answer;
    TransItem;
    WalkItem;
    NeedWant;
  fun
    GoTo : NeedWant -> Dummy -> Phrase ;
    GoByCarTo : NeedWant -> Dummy -> Phrase ;
    GoByCarFromTo : NeedWant -> Dummy -> Dummy -> Phrase ;
    GoToVia : NeedWant -> Dummy -> Dummy -> Phrase; 
    GoFromToVia : NeedWant -> Dummy -> Dummy -> Dummy -> Phrase;
    WalkTo : NeedWant -> Dummy -> Phrase;
    DString : Dummy ;
    GoFromTo : NeedWant -> Dummy -> Dummy -> Phrase;
    FindStore : StoreType -> Phrase ; 
    WalkOrTrans : Answer -> Phrase;
    Walking : WalkItem -> Answer;
    Transportation : TransItem -> Answer;
    Need: NeedWant;
    Want: NeedWant;
    Walk,Walk2,Foot : WalkItem;
    Car,Vehicle : TransItem;
    TBy: TransItem -> TransItem;
}
