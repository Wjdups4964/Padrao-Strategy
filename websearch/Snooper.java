/**
 * Watches the search queries
 */
public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        model.addQueryObserver( new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("Query: " + query);
            }
        });
    }
}
