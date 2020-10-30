export const initialState = {
	user: null,
	notification: null,
}

export const actionTypes = {
	SET_USER: "SET_USER",
	SET_NOTIFICATION: "SET_NOTIFICATION",
	CLEAR_NOTIFICATION: "CLEAR_NOTIFICATION",
}

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.SET_USER:
			return {
				...state,
				user: action.user
			}
		case actionTypes.SET_NOTIFICATION:
			return {
				...state,
				notification: action.notification
			}
		case actionTypes.CLEAR_NOTIFICATION:
			return {
				...state,
				notification: null
			}
		default:
			return state;

	}
}

export default reducer;