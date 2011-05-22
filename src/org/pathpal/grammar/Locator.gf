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
    GoItem;
    FromDummy;
    ToDummy;
    ViaDummy;
  fun
    AndThen : GoItem -> Phrase; 
    INeedWantTo : NeedWant -> GoItem -> Phrase; 
    JustGo : GoItem -> Phrase ;

    GoTo : ToDummy -> GoItem ;
    GoTo2 : ToDummy -> GoItem;
    FromTo : FromDummy -> ToDummy -> GoItem;
    GoFrom : FromDummy -> GoItem;
    GoByCar : GoItem ;
    GoByCarTo : ToDummy -> GoItem ;
    GoByCarFromTo : FromDummy -> ToDummy -> GoItem ;
    GoToVia : ToDummy -> ViaDummy -> GoItem; 
    GoFromToVia : FromDummy -> ToDummy -> ViaDummy -> GoItem;
    WalkTo : ToDummy -> GoItem;
    GoFromTo : FromDummy -> ToDummy -> GoItem;

    To : Dummy -> ToDummy;
    From : Dummy -> FromDummy;
    Via : Dummy -> ViaDummy;
    DString : Dummy ;

    WalkOrTrans : Answer -> Phrase;
    Walking : WalkItem -> Answer;
    Transportation : TransItem -> Answer;
    Need: NeedWant;
    Want: NeedWant;
    Walk,Walk2,Foot : WalkItem;
    Car,Vehicle : TransItem;
    TBy: TransItem -> TransItem;

}
