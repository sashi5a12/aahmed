<div class="boxContent">
	<!-- POD CONTENT START HERE -->
	<div class="subHead">
		Searching Filter
	</div>
	<div class="pR10">




		<table width="100%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td colspan="2">

					<table width="100%" border="0" cellpadding="0" cellspacing="0">

						<tr>

							<s:form name="searchUsers" action="searchUsersAction" method="post" theme="simple" >
							
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="0">
									<tr>

										<td>
											<table width="100%" border="0" cellpadding="3"
												cellspacing="0">
												<tr>
													<td class="label boldText">

														User Type:
														<br />
														<s:select name="userToSearch.userType.typeId" list="userTypesList" listValue="typeValue" label="User Type" listKey="typeId" headerValue="All" headerKey="-1" cssClass="inputFieldForm"/>
													</td>

													<td class="label boldText" colspan="2">
														User Name:
														<br />
														<s:textfield name="userToSearch.userName" maxlength="20" size="25" cssClass="inputFieldForm"/>
													</td>

												</tr>

												<tr>
													<td width="35%" class="label boldText">

														First Name :
														<table width="210" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td>
																	<s:textfield name="userToSearch.firstName" maxlength="20" size="25"  id="firstName" cssClass="inputFieldForm"/>
																</td>
															</tr>
														</table>
													</td>


													<td width="31%" class="label boldText">
														Last Name:
														<table width="210" border="0" cellspacing="0"
															cellpadding="0">

															<tr>
																<td>
																	<s:textfield name="userToSearch.lastName" maxlength="20" size="25" id="lastName" cssClass="inputFieldForm"/>
																</td>
															</tr>
														</table>
													</td>


													<td width="34%" valign="bottom">
														<!-- <input type="submit" id="searchUsersAction_0" value="Search" class="input_primary mR5"/>
 -->

														<button class="input_primary">
															<span><span><span>Search</span>
															</span>
															</span>
														</button>
													</td>
												</tr>
											</table>
										</td>
									</tr>

								</table>
							</td>

							</form>




						</tr>
					</table>
				</td>
			</tr>

		</table>
		<!-- ends here--->
		</td>

		</tr>



		</table>


		<div class="clear10"></div>

	</div>
	<!-- POD CONTENT END HERE -->
</div>
