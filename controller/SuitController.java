package controller;

import model.SuitDatabase;
import model.SuperheroSuit;
import view.SuitView;
import java.util.List;
import java.util.Optional;

public class SuitController {
    private List<SuperheroSuit> suits;

    public SuitController() {
        suits = SuitDatabase.loadSuits();
    }

    public void run() {
        while (true) {
            String suitId = SuitView.getSuitIdInput();
            Optional<SuperheroSuit> suitOpt = suits.stream().filter(s -> s.getSuitId().equals(suitId)).findFirst();

            if (!suitOpt.isPresent()) {
                SuitView.showMessage("Suit not found in the database!");
                continue;
            }

            SuperheroSuit suit = suitOpt.get();
            SuitView.showSuitInfo(suit);

            if (!suit.isValid()) {
                SuitView.showMessage("This suit does not meet durability conditions! It needs repair.");
                if (SuitView.askForRepair()) {
                    suit.repair();
                    SuitDatabase.saveSuits(suits);
                    SuitView.showMessage("Suit has been successfully repaired!");
                }
            }
        }
    }
}
