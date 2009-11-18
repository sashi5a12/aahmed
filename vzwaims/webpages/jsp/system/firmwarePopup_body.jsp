<script language="javascript">
	function submit(flag){
		var opener=window.opener;								
		if (opener && opener.document.forms[0].name=='FirmwareForm'){
			if (flag){
				opener.document.forms[0].sendMail.value="true";
			}
			opener.create();
		}
		self.close();
	}
</script>
<DIV class="contentbox">
	<DIV class="homeColumnTab">
		<table width="500px" border="0" cellspacing="0" cellpadding="0"  class="GridGradient" style="margin-left: 13px">
			<tr>
				<td class="noPad" style="width:500px">
					<DIV class="homeColumnTab" style="width:500px">
						<DIV class="headLeftCurveblk"></DIV>
						<H1 style="width:480px">Send Mail to VZAppZone Developer(s)</H1>
						<DIV class="headRightCurveblk"></DIV>
					</div>				
				</td>
			</tr>
			<tr>
				<td class="gradient" style="width:500px">
					Would you like to send an email broadcast to all alliances that
					have VZAppZone application, to inform them of the new device?
				</td>
			</tr>
			<tr>
				<td style="width:500px">
					<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="No">
						<div><div><div onClick="submit(false);">No</div></div></div>
					</div>
				
					<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="Yes">
						<div><div><div onClick="submit(true)">Yes</div></div></div>
					</div>

				</td>
			</tr>
		</table>
	</DIV>
</DIV>
