ALTER TABLE stock_snapshot
    ADD COLUMN IF NOT EXISTS net_profit_margin_pct NUMERIC,
    ADD COLUMN IF NOT EXISTS operating_margin_pct NUMERIC,
    ADD COLUMN IF NOT EXISTS sales_margin_pct NUMERIC,
    ADD COLUMN IF NOT EXISTS return_on_assets_pct NUMERIC,
    ADD COLUMN IF NOT EXISTS return_on_equity_pct NUMERIC;