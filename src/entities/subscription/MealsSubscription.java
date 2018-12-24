/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.subscription;

import javax.persistence.Entity;
import javax.persistence.Table;

import static utils.Constants.DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name="Subscriptions")
public class MealsSubscription extends AbstractSubscription {

    public MealsSubscription() {
        super(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT,SubscriptionType.MEALS);
    }
}
