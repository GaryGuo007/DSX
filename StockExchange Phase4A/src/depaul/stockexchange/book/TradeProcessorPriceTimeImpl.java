/*
 * Copyright 2015 jimliu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package depaul.stockexchange.book;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.Tradable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements the TradeProcessor interface.
 *
 * @author jimliu
 */
public class TradeProcessorPriceTimeImpl implements TradeProcessor {

    private HashMap<String, FillMessage> fillMessages = new HashMap<>();
    private ProductBookSide side;

    void setBookSide(ProductBookSide productBookSide) throws DataValidationException {
        if (productBookSide == null) {
            throw new DataValidationException("Invalid argument: BookSide cann't be null");
        }
        this.side = productBookSide;
    }

    public TradeProcessorPriceTimeImpl(ProductBookSide productBookSide)
            throws DataValidationException {
        setBookSide(productBookSide);
    }

    private String makeFillKey(FillMessage fm) {
        return fm.getUser() + fm.getId() + fm.getPrice().toString();
    }

    private boolean isNewFill(FillMessage fm) {
        String key = makeFillKey(fm);
        if (!fillMessages.containsKey(key)) {
            return true;
        }
        FillMessage oldFill = fillMessages.get(key);
        if (oldFill.getSide() != fm.getSide()) {
            return true;
        }
        if (!oldFill.getId().equals(fm.getId())) {
            return true;
        }
        return false;
    }

    private void addFillMesaage(FillMessage fm)
            throws DataValidationException {
        String key = makeFillKey(fm);
        if (isNewFill(fm)) {
            fillMessages.put(key, fm);
        } else {
            fillMessages.get(key).setVolume(
                    fillMessages.get(key).getVolume() + fm.getVolume());
            fillMessages.get(key).setDetails(fm.getDetails());
        }
    }

    @Override
    public HashMap<String, FillMessage> doTrade(Tradable trd)
            throws DataValidationException {
        if (trd == null) {
            throw new DataValidationException("Invalid argument: trd cann't be null");
        }
        fillMessages.clear();
        ArrayList<Tradable> tradedOut = new ArrayList<>();
        ArrayList<Tradable> entriesAtPrice = side.getEntriesAtTopOfBook();
        for (Tradable t : entriesAtPrice) {
            if (trd.getRemainingVolume() == 0) {
                break;
            }

            Price tPrice = t.getPrice().isMarket() ? trd.getPrice() : t.getPrice();

            if (trd.getRemainingVolume() >= t.getRemainingVolume()) {

                tradedOut.add(t);

                addFillMesaage(new FillMessage(t.getUser(), t.getProduct(), tPrice,
                        t.getRemainingVolume(), "leaving 0", t.getSide(), t.getId()));
                addFillMesaage(new FillMessage(trd.getUser(), trd.getProduct(), tPrice,
                        t.getRemainingVolume(), "leaving "
                        + (trd.getRemainingVolume() - t.getRemainingVolume()),
                        trd.getSide(), trd.getId()));

                trd.setRemainingVolume(trd.getRemainingVolume() - t.getRemainingVolume());
                t.setRemainingVolume(0);
                side.addOldEntry(t);
            } else {
                int remainder = t.getRemainingVolume() - trd.getRemainingVolume();

                addFillMesaage(new FillMessage(t.getUser(), t.getProduct(), tPrice,
                        trd.getRemainingVolume(), "leaving " + remainder, t.getSide(), t.getId()));
                addFillMesaage(new FillMessage(trd.getUser(), trd.getProduct(), tPrice,
                        trd.getRemainingVolume(), "leaving 0", trd.getSide(), trd.getId()));

                trd.setRemainingVolume(0);
                t.setRemainingVolume(remainder);
                side.addOldEntry(trd);
                break;
            }
        }

        for (Tradable tOut : tradedOut) {
            entriesAtPrice.remove(tOut);
        }

        if (entriesAtPrice.isEmpty()) {
            side.clearIfEmpty(side.topOfBookPrice());
        }
        return fillMessages;
    }

}
