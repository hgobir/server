package com.fdm.server.project.server.trade;

import org.springframework.stereotype.Component;

public enum OrderRequestStatus {
    INITIATED,
    PENDING,
    SUCCESSFULLY_COMPLETED,
    USER_DOESNT_HAVE_ENOUGH_MONEY,
    USER_DOESNT_HAVE_ANY_MONEY,
    USER_DOESNT_HAVE_ENOUGH_SELLABLE_SHARES,
    USER_DOESNT_HAVE_ANY_SELLABLE_SHARES,
    NOT_ENOUGH_AVAILABLE_SHARES_IN_MARKET
}
