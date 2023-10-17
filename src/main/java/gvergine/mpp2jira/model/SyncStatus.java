package gvergine.mpp2jira.model;

public enum SyncStatus
{
	UNKNOWN,		/*    Jira was never contacted to check if the dates match  */
	ERROR,			/* x  Jira was contacted to check if the dates match but transport error */
	CHECKING, 		/* o  Jira is being contacted to check if dates match */
	SAME, 			/* v  dates match */
    BEFORE, 		/* <  dates don't match, jira date is before */
	AFTER, 			/* >  dates don't match, jira date is later */
}
