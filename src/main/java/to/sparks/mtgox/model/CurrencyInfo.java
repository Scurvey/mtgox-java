package to.sparks.mtgox.model;

import java.io.IOException;
import java.util.Currency;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.annotate.JsonProperty;
import to.sparks.mtgox.net.JSONSource;

/**
 * Contains information about an MtGox currency
 *
 * @author SparksG
 */
public class CurrencyInfo extends DtoBase {

    private String currency_code;
    private String name;
    private String symbol;
    private int decimals;
    private int display_decimals;
    private String symbol_position;
    private boolean virtual;
    private String ticker_channel;
    private String depth_channel;
    private static final String BITCOIN_INFO_JSON = "{\"result\":\"success\",\"return\":{\"currency\":\"BTC\",\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"decimals\":\"8\",\"display_decimals\":\"2\",\"symbol_position\":\"after\",\"virtual\":\"Y\",\"ticker_channel\":\"13edff67-cfa0-4d99-aa76-52bd15d6a058\",\"depth_channel\":\"7d3d7ae3-7da7-48cf-9c82-51d7ab3fe60f\"}}";
    public static CurrencyInfo BitcoinCurrencyInfo;

    static {
        try {
            BitcoinCurrencyInfo = new JSONSource<Result<CurrencyInfo>>().getResultFromString(BITCOIN_INFO_JSON, CurrencyInfo.class).getReturn();
        } catch (IOException ex) {
            Logger.getLogger(CurrencyInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CurrencyInfo(@JsonProperty("currency") String currency_code,
            @JsonProperty("name") String name,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("decimals") int decimals,
            @JsonProperty("display_decimals") int display_decimals,
            @JsonProperty("symbol_position") String symbol_position,
            @JsonProperty("virtual") String virtual,
            @JsonProperty("ticker_channel") String ticker_channel,
            @JsonProperty("depth_channel") String depth_channel) {
        this.currency_code = currency_code;
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
        this.display_decimals = display_decimals;
        this.symbol_position = symbol_position;
        this.virtual = virtual.trim().equalsIgnoreCase("Y");
        this.ticker_channel = ticker_channel;
        this.depth_channel = depth_channel;
    }

    public Currency getCurrency() {
        if (isVirtual()) {
            throw new UnsupportedOperationException("Virtual MtGox currencies cannot be expressed as a Java currency.");
        }
        return Currency.getInstance(currency_code);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDecimals() {
        return decimals;
    }

    public int getDisplay_decimals() {
        return display_decimals;
    }

    public String getSymbol_position() {
        return symbol_position;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public String getTicker_channel() {
        return ticker_channel;
    }

    public String getDepth_channel() {
        return depth_channel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrencyInfo other = (CurrencyInfo) obj;
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.symbol);
        return hash;
    }
}
