public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        model.addQueryObserver(new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("Oh Yes! " + query.trim());
            }
        }, new QueryFilter() {
            @Override
            public boolean matches(String query) {
                return query.toLowerCase().contains("friend");
            }
        });

        model.addQueryObserver(new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("So long " + query.trim());
            }
        }, new QueryFilter() {
            @Override
            public boolean matches(String query) {
                return query.length() > 60;
            }
        });
    }
}